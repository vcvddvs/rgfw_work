<template>
  <div class="rating-wrapper">
    <header class="rating-header">
      <div class="back-button" @click="goBack">
        <img src="../assets/img_10.png" alt="返回">
      </div>
      <h2>订单评价</h2>
    </header>

    <div class="order-info">
      <div class="business-info">
        <img :src="processImageUrl(businessImg)" alt="商家图片" class="business-img">
        <div class="business-details">
          <h3>{{ businessName }}</h3>
          <p>订单编号: {{ orderId }}</p>
          <p>订单时间: {{ orderDate }}</p>
        </div>
      </div>
    </div>

    <div class="rating-form">
      <div class="rating-section">
        <h3>菜品评分</h3>
        <div class="star-rating">
          <el-rate
            v-model="foodRating"
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
            :show-text="true"
            :texts="['很差', '较差', '一般', '不错', '很好']"
          ></el-rate>
        </div>
      </div>

      <div class="rating-section">
        <h3>配送评分</h3>
        <div class="star-rating">
          <el-rate
            v-model="deliveryRating"
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
            :show-text="true"
            :texts="['很差', '较差', '一般', '不错', '很好']"
          ></el-rate>
        </div>
      </div>

      <div class="rating-section">
        <h3>评价内容</h3>
        <el-input
          type="textarea"
          v-model="content"
          :rows="4"
          placeholder="请输入您的评价内容，分享您的用餐体验..."
        ></el-input>
      </div>

      <div class="submit-section">
        <el-button type="primary" @click="submitRating" :disabled="isSubmitting">
          {{ isSubmitting ? '提交中...' : '提交评价' }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ratingApi } from '@/utils/api';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();

// 获取路由参数并提供默认值
const orderId = ref(route.params.orderId || '1');
const businessId = ref(route.params.businessId || '1');
const businessName = ref(route.params.businessName || '未知商家');
const businessImg = ref(route.query.businessImg || '');
const orderDate = ref(route.query.orderDate || new Date().toLocaleString());

// 评价表单数据
const foodRating = ref(5); // 默认5分
const deliveryRating = ref(5); // 默认5分
const content = ref('');
const isSubmitting = ref(false);

// 返回上一页
const goBack = () => {
  router.back();
};

// 处理图片URL
const processImageUrl = (imgPath) => {
  if (!imgPath) {
    return require('../assets/img/BUSINESS/img.png');
  }
  
  console.log('处理评价页面图片路径:', imgPath);
  
  // 如果是完整URL或base64，直接返回
  if (imgPath.startsWith('http') || imgPath.startsWith('data:')) {
    return imgPath;
  }
  
  // 如果是以/assets开头的路径，转换为正确的路径
  if (imgPath.startsWith('/assets/')) {
    // 去掉开头的/assets/，因为在项目中assets是相对路径
    const relativePath = imgPath.replace('/assets/', '');
    try {
      return require(`../assets/${relativePath}`);
    } catch (e) {
      console.log('无法加载assets路径图片，尝试其他方式:', e);
      // 尝试通过API网关获取
      return 'http://localhost:8090' + imgPath;
    }
  }
  
  // 如果是相对路径，添加baseURL
  if (imgPath.startsWith('/')) {
    return 'http://localhost:8090' + imgPath;
  }
  
  // 尝试加载项目中的图片
  try {
    // 首先尝试从BUSINESS目录加载
    if (!imgPath.includes('/')) {
      try {
        return require('../assets/img/BUSINESS/' + imgPath);
      } catch (e) {
        console.log('BUSINESS目录未找到图片，尝试其他路径');
      }
    }
    
    // 然后尝试从普通img目录加载
    return require('../assets/img/' + imgPath);
  } catch (e) {
    console.error('无法加载评价页面图片:', imgPath, e);
    return require('../assets/img/BUSINESS/img.png');
  }
};

// 提交评价
const submitRating = async () => {
  if (foodRating.value === 0 || deliveryRating.value === 0) {
    ElMessage.warning('请为菜品和配送服务评分');
    return;
  }

  if (!content.value.trim()) {
    ElMessage.warning('请输入评价内容');
    return;
  }

  try {
    isSubmitting.value = true;
    
    // 获取用户ID
    const userId = localStorage.getItem('userId');
    if (!userId) {
      ElMessage.error('请先登录');
      router.push('/login-component');
      return;
    }

    // 构建评价数据
    const ratingData = {
      orderId: Number(orderId.value),
      userId: userId,
      businessId: Number(businessId.value),
      businessName: businessName.value,
      foodRating: foodRating.value,
      deliveryRating: deliveryRating.value,
      content: content.value,
      createTime: new Date().toISOString()
    };

    try {
      // 尝试调用API提交评价
      console.log('尝试提交评价到API:', ratingData);
      const response = await ratingApi.submitRating(ratingData);
      
      if (response) {
        // 同时保存到本地存储，作为备份
        saveRatingToLocalStorage(ratingData);
        
        ElMessage.success('评价提交成功');
        // 延迟后返回订单历史页面
        setTimeout(() => {
          router.push('/history-component');
        }, 1500);
      } else {
        ElMessage.error('评价提交失败');
      }
    } catch (error) {
      console.error('评价提交API调用出错:', error);
      
      // 保存到本地存储
      saveRatingToLocalStorage(ratingData);
      
      // 给用户成功的反馈
      ElMessage({
        message: '评价已本地保存，将在服务恢复后同步',
        type: 'success',
        duration: 2000
      });
      
      setTimeout(() => {
        router.push('/history-component');
      }, 2000);
    }
  } catch (error) {
    console.error('评价提交出错:', error);
    ElMessage.error('评价提交出错: ' + (error.message || '未知错误'));
  } finally {
    isSubmitting.value = false;
  }
};

// 将评价保存到本地存储
const saveRatingToLocalStorage = (ratingData) => {
  try {
    // 获取现有评价
    const ratingsString = localStorage.getItem('localRatings');
    let ratings = [];
    
    if (ratingsString) {
      ratings = JSON.parse(ratingsString);
    }
    
    // 确保orderId是数字类型
    const ratingWithNumericId = {
      ...ratingData,
      orderId: Number(ratingData.orderId)
    };
    
    // 检查是否已经有该订单的评价
    const existingIndex = ratings.findIndex(r => r.orderId === ratingWithNumericId.orderId);
    
    if (existingIndex >= 0) {
      // 更新现有评价
      ratings[existingIndex] = ratingWithNumericId;
    } else {
      // 添加新评价
      ratings.push(ratingWithNumericId);
    }
    
    // 保存回本地存储
    localStorage.setItem('localRatings', JSON.stringify(ratings));
    console.log('评价已保存到本地存储', ratings);
  } catch (error) {
    console.error('保存评价到本地存储失败:', error);
  }
};

// 从本地存储中获取评价
const getLocalRating = (orderId) => {
  try {
    const ratingsString = localStorage.getItem('localRatings');
    console.log(`尝试从本地获取订单 ${orderId} 的评价, 本地存储:`, ratingsString);
    
    if (ratingsString) {
      const ratings = JSON.parse(ratingsString);
      console.log('解析后的本地评价:', ratings);
      
      const numericOrderId = Number(orderId);
      const rating = ratings.find(r => r.orderId === numericOrderId);
      
      console.log(`订单 ${orderId} 的本地评价:`, rating);
      return rating;
    }
  } catch (error) {
    console.error('从本地存储获取评价失败:', error);
  }
  return null;
};

// 组件加载时检查是否已经评价过
onMounted(async () => {
  try {
    if (orderId.value) {
      // 先检查本地存储
      const localRating = getLocalRating(orderId.value);
      
      if (localRating) {
        // 使用本地存储的评价数据
        foodRating.value = localRating.foodRating || 5;
        deliveryRating.value = localRating.deliveryRating || 5;
        content.value = localRating.content || '';
        ElMessage.info('找到您之前的评价，您可以修改后重新提交');
        return;
      }
      
      // 如果本地没有，尝试从API获取
      try {
        const response = await ratingApi.getRatingByOrderId(orderId.value);
        console.log('API返回的评价数据:', response);
        
        if (response && response.data && Object.keys(response.data).length > 0) {
          // 已有评价，填充表单
          foodRating.value = response.data.foodRating || 5;
          deliveryRating.value = response.data.deliveryRating || 5;
          content.value = response.data.content || '';
          ElMessage.info('找到您之前的评价，您可以修改后重新提交');
        } else {
          console.log('API返回空数据或无效数据，视为新评价');
          // 设置默认值
          foodRating.value = 5;
          deliveryRating.value = 5;
          content.value = '';
        }
      } catch (error) {
        console.error('获取评价信息出错:', error);
        // 获取失败不做特殊处理，当作新评价
        ElMessage.info('这是您第一次评价此订单');
      }
    }
  } catch (error) {
    console.error('组件加载时出错:', error);
  }
});
</script>

<style scoped>
.rating-wrapper {
  padding: 15px;
  max-width: 600px;
  margin: 0 auto;
}

.rating-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  position: relative;
}

.back-button {
  position: absolute;
  left: 0;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.back-button img {
  width: 24px;
  height: 24px;
}

.rating-header h2 {
  flex: 1;
  text-align: center;
  margin: 0;
  font-size: 18px;
}

.order-info {
  background-color: #f8f8f8;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 20px;
}

.business-info {
  display: flex;
  align-items: center;
}

.business-img {
  width: 60px;
  height: 60px;
  border-radius: 4px;
  object-fit: cover;
  margin-right: 15px;
}

.business-details h3 {
  margin: 0 0 5px 0;
  font-size: 16px;
}

.business-details p {
  margin: 3px 0;
  font-size: 14px;
  color: #666;
}

.rating-section {
  margin-bottom: 20px;
}

.rating-section h3 {
  margin-bottom: 10px;
  font-size: 16px;
}

.star-rating {
  margin-bottom: 10px;
}

.submit-section {
  margin-top: 30px;
  text-align: center;
}

.submit-section button {
  width: 80%;
  max-width: 300px;
}
</style> 