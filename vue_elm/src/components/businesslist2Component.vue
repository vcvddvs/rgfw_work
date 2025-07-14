<template>
  <div class="wrapper">
    <div class="header">
      <div class="title">
        <div class="img1" @click="goBack">
          <img :src="require('@/assets/img/BUSINESS/img_1.png')" height="22" width="13"/>
        </div>
        <div class="img2">
          <img :src="require('@/assets/img/BUSINESS/img.png')" height="auto" width="100%"/>
        </div>
      </div>
      <div class="header-all">
        <div class="header-text">
          <div class="text-left-header">
            <h>{{businessName}}</h>
          </div>
          <div class="star">
            <p1>{{ businessDetails.starPrice || '暂无' }}分 </p1>
            <p2> 蜂鸟准时达</p2>
            <p3>约{{ businessDetails.deliveryPrice || '暂无' }}分钟  月售{{ businessDetails.orderTypeId || '0' }}+</p3>
          </div>
          <div class="ticket">
            <img :src="require('@/assets/img/BannerImg/img_1.png')" height="23" width="100"/>
          </div>
          <div class="for-sell">
            <p1>起送费: {{ businessDetails.starPrice || '暂无' }}元</p1>
            <p2>配送费: {{ businessDetails.deliveryPrice || '暂无' }}元</p2>
          </div>
        </div>
        <div class="text-picture">
          <img :src="processImageUrl(businessImg)" height="155" width="157"/>
        </div>
      </div>
      <div class="banner">
        <img :src="require('@/assets/img/BannerImg/img_3.png')" height="15" width="100"/>
        <p1>{{ businessDetails.businessExplain || '商家说明' }}</p1>
        <p2>{{ businessDetails.remarks || '每日更新' }}</p2>
      </div>
    </div>
    <div class="body">
      <div class="recommend">
        <div class="recommend-left">
          <div>
            <p1>点餐</p1>
          </div>
          <div>
            <p2>评价</p2>
          </div>
          <div>
            <p3>品牌</p3>
          </div>
        </div>
        <div class="recommend-right">
          <p>好友拼单</p>
        </div>
      </div>

      <div class="foodList">
        <div class="foodList-left">
          <div class="title0">
            <img :src="require('@/assets/img/BUSINESS/title/img.png')" height="26" width="30"/>
            <p> 学生专享</p>
          </div>
          <div class="title1">
            <img :src="require('@/assets/img/BUSINESS/title/img_1.png')" height="26" width="30"/>
            <p>热销</p>
          </div>
          <div class="title1">
            <img :src="require('@/assets/img/BUSINESS/title/img.png')" height="26" width="30"/>
            <p> 优惠</p>
          </div>
          <div class="title2">
            <p>热销必点</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_2.png')" height="16" width="46"/>
          </div>
          <div class="title2">
            <p>招牌全鸡</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_4.png')" height="20" width="42"/>
          </div>
          <div class="title2">
            <p>休闲小吃</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_5.png')" height="22" width="39"/>
          </div>
          <div class="title2">
            <p>卷堡主食</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_6.png')" height="20" width="42"/>
          </div>
          <div class="title2">
            <p>饮品专区</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_7.png')" height="20" width="40"/>
          </div>
          <div class="title2">
            <p>调料专区</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_8.png')" height="18" width="40"/>
          </div>
          <div class="title2">
            <p>品牌活动</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_9.png')" height="19" width="41"/>
          </div>
        </div>
        <div class="foodList-right">
          <div class="menus" v-for="food in foods" :key="food.foodId">
            <div class="menus-body">
              <div class="menus-body-left">
                <img :src="processImageUrl(food.foodImg)" 
                     height="124" 
                     width="150"
                     @error="handleImageError" />
              </div>
              <div class="menus-body-right">
                <div class="mbr-title">
                  <p1>{{ food.foodName || '未命名菜品' }}</p1>
                </div>
                <div class="mbr-banner">
                  <p2>{{ food.foodExplain || '暂无说明' }}</p2>
                </div>
                <div class="mbr-count">
                  <p3>{{ food.remarks || '暂无销售数据' }}</p3>
                </div>
                <div class="mbr-sell" v-if="food.remarks && food.remarks.includes('折')">
                  <p4>{{ food.remarks }}</p4>
                  <p5>优惠价</p5>
                </div>
                <div class="mbr-price">
                  <p>&#165;{{ formatPrice(food.foodPrice) }}</p>
                  <div class="cart-actions">
                    <button 
                      v-if="cart[food.foodId] && cart[food.foodId].quantity > 0"
                      class="cart-btn minus-btn"
                      @click="decrementQuantity(food.foodId)"
                    >
                      <i class="fa fa-minus"></i>
                    </button>
                    <span 
                      v-if="cart[food.foodId] && cart[food.foodId].quantity > 0"
                      class="cart-quantity"
                    >
                      {{ cart[food.foodId].quantity }}
                    </span>
                    <button 
                      class="cart-btn plus-btn"
                      @click="addToCart(food)"
                    >
                      <i class="fa fa-plus"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 购物车底部栏 -->
    <div class="footer">
      <div class="cart-left" :class="{ 'has-items': totalQuantity > 0 }" @click="toggleCart">
        <div class="cart-left-icon">
          <i class="fa fa-shopping-cart"></i>
        </div>
        <!-- 当购物车中有商品时显示数量 -->
        <div class="cart-left-icon-quantity" v-if="totalQuantity > 0">{{ totalQuantity }}</div>
        <div class="cart-price" v-if="totalQuantity > 0">¥{{ formatPrice(totalPrice) }}</div>
        <div class="cart-empty" v-else>购物车是空的</div>
      </div>
      <div class="cart-right">
        <div class="cart-right-item" @click="checkout" :class="{ 'active': totalQuantity > 0 }">
          {{ totalQuantity > 0 ? '去结算' : '未选购商品' }}
        </div>
      </div>
    </div>
    
    <!-- 购物车弹出层 -->
    <div class="cart-container" v-if="showCart && totalQuantity > 0">
      <div class="cart-overlay" @click="showCart = false"></div>
      <div class="cart-content">
        <div class="cart-header">
          <h3>购物车</h3>
          <div class="cart-clear" @click="clearCart">
            <i class="fa fa-trash"></i> 清空购物车
          </div>
        </div>
        <div class="cart-items">
          <div v-for="item in Object.values(cart)" :key="item.foodId" class="cart-item">
            <div class="cart-item-info">
              <img :src="processImageUrl(item.foodImg)" :alt="item.foodName" class="cart-item-img">
              <span class="cart-item-name">{{ item.foodName }}</span>
            </div>
            <div class="cart-item-price">¥{{ formatPrice(item.price) }}</div>
            <div class="quantity-controls">
              <button @click="removeFromCart(item)" class="quantity-btn minus">
                <i class="fa fa-minus"></i>
              </button>
              <span class="quantity-value">{{ item.quantity }}</span>
              <button @click="addToCart(item)" class="quantity-btn plus">
                <i class="fa fa-plus"></i>
              </button>
            </div>
          </div>
        </div>
        <div class="cart-footer">
          <div class="total">总计: ¥{{ formatPrice(totalPrice) }}</div>
          <button @click="checkout" class="checkout-btn">去结算</button>
        </div>
        <!-- 添加额外的底部填充区域，确保覆盖底部购物车 -->
        <div class="cart-bottom-padding"></div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import { cartApi, businessApi } from '@/utils/api';
import { getToken } from '@/utils/auth';

const router = useRouter();
const route = useRoute();

const businessImg = ref(route.params.businessImg);
const businessId = ref(route.params.businessId);
const businessName = ref(route.params.businessName || '默认店铺名称');
const foods = ref([]);
const cart = ref({});
const businessDetails = ref({});
const showCart = ref(false);

console.log(businessId.value);

// 删除未使用的fetchBusinessDetails和fetchFoods函数

onMounted(async () => {
  try {
    // 获取商家详情
    const response = await businessApi.getBusinessById(businessId.value);
    console.log('商家详情:', response);
    
    if (response) {
      businessDetails.value = response;
      if (!businessName.value || businessName.value === '默认店铺名称') {
        businessName.value = response.businessName || '默认店铺名称';
      }
      if (!businessImg.value) {
        businessImg.value = response.businessImg || '';
      }
    }
    
    // 获取食品列表
    const foodsResponse = await businessApi.getFoodsByBusinessId(businessId.value);
    console.log('食品列表:', foodsResponse);
    
    if (foodsResponse && Array.isArray(foodsResponse)) {
      foods.value = foodsResponse;
    } else if (foodsResponse && foodsResponse.data && Array.isArray(foodsResponse.data)) {
      foods.value = foodsResponse.data;
    } else {
      console.error('获取食品列表失败，响应格式不正确:', foodsResponse);
    }
    
    // 加载本地存储中的购物车信息
    loadCartFromLocalStorage();
    
  } catch (error) {
    console.error('获取商家详情或食品列表出错:', error);
    ElMessage.error('获取商家信息失败');
  }
});

// 从本地存储加载购物车信息
const loadCartFromLocalStorage = () => {
  try {
    const cartInfoStr = localStorage.getItem('cartInfo');
    if (cartInfoStr) {
      const cartInfo = JSON.parse(cartInfoStr);
      
      // 检查是否是当前商家的购物车
      if (cartInfo.businessId === businessId.value) {
        console.log('从本地存储加载购物车信息:', cartInfo);
        cart.value = cartInfo.cart || {};
        
        // 如果商家名称为默认值，尝试使用本地存储中的值
        if (businessName.value === '默认店铺名称' && cartInfo.businessName) {
          businessName.value = cartInfo.businessName;
        }
        
        // 如果商家图片为空，尝试使用本地存储中的值
        if (!businessImg.value && cartInfo.businessImg) {
          businessImg.value = cartInfo.businessImg;
        }
      } else {
        console.log('本地存储中的购物车不属于当前商家，不加载');
      }
    }
  } catch (error) {
    console.error('加载购物车信息失败:', error);
  }
};

// 结算功能
const checkout = async () => {
  try {
    // 检查是否登录
    const token = getToken();
    const userId = localStorage.getItem('userId');
    
    if (!token || !userId) {
      ElMessage.warning('请先登录');
      router.push('/login-component');
      return;
    }

    // 检查购物车是否为空
    if (Object.keys(cart.value).length === 0) {
      ElMessage.warning('购物车为空');
      return;
    }

    // 准备结算数据
    const checkoutData = {
      userId: userId,
      businessId: parseInt(businessId.value),  // 确保是数字类型
      businessName: businessName.value || '',  // 添加商家名称
      businessImg: await getImageBase64(businessImg.value || businessDetails.value?.businessImg || ''),  // 转换为Base64
      items: await Promise.all(Object.values(cart.value).map(async (item) => ({
        foodId: parseInt(item.foodId),  // 确保是数字类型
        quantity: parseInt(item.quantity),  // 确保是数字类型
        price: parseFloat(item.price),  // 确保是数字类型
        foodName: item.foodName || '',  // 添加食品名称
        foodImg: await getImageBase64(item.foodImg || ''),  // 转换为Base64
        businessName: businessName.value || item.businessName || '',  // 保存商家名称
        businessImg: await getImageBase64(businessImg.value || item.businessImg || '')  // 转换为Base64
      })))
    };

    console.log('发送结算请求:', checkoutData);
    console.log('购物车数据:', cart.value);
    console.log('商家ID:', businessId.value);

    const response = await cartApi.checkout(checkoutData);
    
    console.log('结算响应:', response);
    
    if (response && response.code === 1) {
      ElMessage.success('结算成功');
      
      // 保存购物车信息到localStorage，以便在订单页面使用
      const orderInfo = {
        cart: cart.value,
        businessId: businessId.value,
        businessName: businessName.value,
        businessImg: businessImg.value || businessDetails.value?.businessImg || '',
        orderDate: new Date().toLocaleString(),
        orderTotal: totalPrice.value,
        items: Object.values(cart.value).map(item => ({
          foodId: item.foodId,
          foodName: item.foodName,
          price: item.price,
          quantity: item.quantity,
          foodImg: item.foodImg || '',
          remarks: item.remarks || ''
        }))
      };
      localStorage.setItem('orderInfo', JSON.stringify(orderInfo));
      
      // 清空当前购物车本地存储
      localStorage.removeItem('cartInfo');
      
      // 跳转到订单页面，带上必要的参数
      router.push({
        name: 'Order',
        params: {
          businessId: businessId.value,
          businessName: businessName.value
        }
      });
    } else {
      throw new Error(response?.msg || '结算失败');
    }
  } catch (error) {
    console.error('结算错误:', error);
    console.error('错误详情:', {
      message: error.message,
      response: error.response,
      status: error.response?.status,
      data: error.response?.data
    });
    
    if (error.isAuthError) {
      ElMessage.error('登录已过期，请重新登录');
      router.push('/login-component');
      return;
    }

    if (error.response) {
      switch (error.response.status) {
        case 400:
          ElMessage.error(`请求数据格式错误: ${error.response.data?.msg || error.response.data}`);
          break;
        case 403:
          ElMessage.error('没有权限进行此操作');
          break;
        case 500:
          ElMessage.error('服务器内部错误');
          break;
        default:
          ElMessage.error(`结算失败: ${error.response.status} - ${error.response.data?.msg || ''}`);
      }
    } else if (error.request) {
      ElMessage.error('无法连接到服务器，请检查网络');
    } else {
      ElMessage.error('结算失败: ' + error.message);
    }
  }
};

// 将图片URL转换为Base64编码
async function getImageBase64(url) {
  // 如果URL为空，返回默认图片的Base64编码
  if (!url) {
    return getDefaultImageBase64();
  }

  // 如果已经是Base64格式，直接返回
  if (url.startsWith('data:image')) {
    return url;
  }

  try {
    // 处理相对路径和绝对路径
    let imageUrl = url;
    
    // 如果是相对路径，需要转换为完整URL
    if (!url.startsWith('http') && !url.startsWith('data:')) {
      // 处理以/assets/开头的路径
      if (url.startsWith('/assets/')) {
        // 使用默认图片
        return getDefaultImageBase64();
      } else {
        // 尝试构建完整URL
        const baseUrl = window.location.origin;
        imageUrl = url.startsWith('/') ? `${baseUrl}${url}` : `${baseUrl}/${url}`;
      }
    }

    // 获取图片并转换为Base64
    const response = await fetch(imageUrl);
    const blob = await response.blob();
    
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result);
      reader.onerror = reject;
      reader.readAsDataURL(blob);
    });
  } catch (error) {
    console.error('获取图片Base64失败:', error);
    return getDefaultImageBase64();
  }
}

// 获取默认图片的Base64编码
function getDefaultImageBase64() {
  // 返回一个简单的默认图片Base64编码
  return 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAOxAAADsQBlSsOGwAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3Njape.org5vuPBoAAANCSURBVGiB7Zm7b9NQFMZ/x4lLHVoVYEAIBBIDA0JiYYCBDYmBiYGJDfEHsDGwMTAiMTIxMCIxMDIgMTAwMCCBQEKCUh6lJA+nKXFsB9uJr69vkzZRzpdUVfE9536+e3zOuecGMmTIkCHDf4toZIGqHgYuAmeAI8DeFuaKgB/wgC/Ae1X92sj8DQOo6gxwA7gM5Hf4nBrwFnioql93Yp8TQFWvAQ+AqZ0Y0wJl4I6qPt+uYVsAqnoJeALkdmpZG9SAW6r6bCsD2wJQ1UvAU5rfvF8oA9dV9UWrm5sAVPUo8JHJbZ4GlWYfuKOqH5pvbDjEqpoDXjJZ8AAOsENVXwG55hsb3oCq3gTu7YVVbfAQuL9+YR1AVQ8BP4HCHhvVCiXgmKr+WruwcYSKwP29t6ktCsBt/ceSAKp6HjjZDYvaQIBLaxfWj9DVrlnTPs6t/VgCUNVp4GxXzWmPM+kxApaP0IUum9IJzutfYwBp8jzTXXs6wllIAFT1GHCADZ7dZewBjqfHaG0FTnTXno5xPP1YAjjeXVs6xon0Yw5AVWeBQ102plMcVNVZWM7EJ7psTKdYysiqehDIddOSHSAHHFRgX7ct2SH2KzDdbSt2iGkFJr66doMZBSa+wHeDaQWcbluxQ7gKFLttxQ5RVGCl21bsECtKm1K1H6HAcretGKMKLAIVwE8/q8CvdG0FqCrg9bF9HtAHZoB9wGy6Ppcee9PjbHrsAQpKm3K1X6gCPxqwwQVOAaeBU+nnSeAY4DZgX1mBxX40dALuAo+Bj8Bn4BtwGTgPzDdgX1mB7/1o6AS8B94Bi8AhYAG4CuQasK+kwGK/AKhqDvAa0F9SVa9Z/6GqLwl5QjMsqepiPwAQR+lGA/pFVfWbFRLSrTcN6BcUWOoHAGLrYKEBfa1ZIaGqZeBHA/q6Av4kA/gNaCsKLKjqn0kFUNUK8K0B/ZICv/sRhQBqDeprCnwH/gJUVcvEjYhJQo04Cv8CUFWfuCExSfCBigKo6gpxZ2JSUCW2Lv4BpMnoC3GnYtxRAr6kf9cBpMnoO+LOxTjDB94sJXJrAaSHo0Tc0Rg3+MAbVV1av7hhK1FVPwGvGa9EXAU+bWwcZsiQIUOG/wF/ASVhoLJ9NEbJAAAAAElFTkSuQmCC';
}

// 添加到购物车
const addToCart = (food) => {
  const cartItem = cart.value[food.foodId];
  
  if (cartItem) {
    cartItem.quantity += 1;
  } else {
    // 保存商品的完整信息，包括图片路径
    cart.value[food.foodId] = {
      foodId: food.foodId,
      foodName: food.foodName,
      price: food.foodPrice,
      quantity: 1,
      businessId: businessId.value,
      businessName: businessName.value,
      businessImg: businessImg.value || businessDetails.value.businessImg || '',
      // 保存食品图片路径，确保使用正确的图片
      foodImg: food.foodImg || '',
      // 保存其他可能需要的信息
      foodDescription: food.foodDescription || '',
      remarks: food.remarks || ''
    };
  }
  
  // 将当前购物车信息保存到本地存储，以便在页面刷新后恢复
  saveCartToLocalStorage();
  
  ElMessage.success('已添加到购物车');
};

// 保存购物车信息到本地存储
const saveCartToLocalStorage = () => {
  try {
    const cartInfo = {
      cart: cart.value,
      businessId: businessId.value,
      businessName: businessName.value,
      businessImg: businessImg.value || businessDetails.value.businessImg || '',
      timestamp: new Date().getTime()
    };
    localStorage.setItem('cartInfo', JSON.stringify(cartInfo));
    console.log('购物车信息已保存到本地存储', cartInfo);
  } catch (error) {
    console.error('保存购物车信息失败:', error);
  }
};

// 从购物车移除
const removeFromCart = (item) => {
  if (cart.value[item.foodId]) {
    if (cart.value[item.foodId].quantity > 1) {
      cart.value[item.foodId].quantity -= 1;
    } else {
      delete cart.value[item.foodId];
    }
    
    // 更新本地存储中的购物车信息
    saveCartToLocalStorage();
    
    ElMessage.success('已从购物车移除');
  }
};

// 计算购物车总价
const totalPrice = computed(() => {
  return Object.values(cart.value).reduce((total, item) => {
    return total + (item.price * item.quantity);
  }, 0);
});

// 计算购物车总数量
const totalQuantity = computed(() => {
  return Object.values(cart.value).reduce((total, item) => {
    return total + item.quantity;
  }, 0);
});

function goBack() {
  router.push('/Index-Component');
}

function handleImageError(event) {
  event.target.src = require('@/assets/img/BUSINESS/menus/img.png');
}

// 处理图片URL，确保能正确显示
function processImageUrl(url) {
  console.log('处理图片URL类型:', typeof url);
  
  // 如果URL为空，返回默认图片
  if (!url) {
    console.log('图片URL为空，使用默认图片');
    return require('@/assets/img/BUSINESS/menus/img.png');
  }
  
  // 如果URL是字符串类型
  if (typeof url === 'string') {
    // 如果已经是完整的URL或data:image格式，直接返回
    if (url.startsWith('http') || url.startsWith('data:')) {
      console.log('使用原始图片URL(Base64或HTTP)');
      return url;
    }
    
    // 处理以/assets/开头的路径
    if (url.startsWith('/assets/')) {
      try {
        // 尝试映射到@/assets/路径
        const assetPath = url.replace('/assets/', '@/assets/');
        console.log('尝试加载资源路径:', assetPath);
        return require(assetPath);
      } catch (e) {
        console.log('资源路径加载失败，尝试使用相对路径');
        try {
          // 尝试直接使用相对路径
          return require('@/assets' + url.substring(7));
        } catch (e2) {
          console.log('相对路径加载失败，使用默认图片');
          return require('@/assets/img/BUSINESS/menus/img.png');
        }
      }
    }
    
    // 尝试作为相对路径处理
    try {
      console.log('尝试作为相对路径处理:', url);
      if (url.includes('/')) {
        // 如果包含路径分隔符，尝试直接require
        return require(url);
      } else {
        // 否则尝试在默认位置查找
        return require('@/assets/img/BUSINESS/menus/' + url);
      }
    } catch (e) {
      console.log('图片路径处理失败，使用默认图片');
      return require('@/assets/img/BUSINESS/menus/img.png');
    }
  }
  
  // 如果URL是对象或其他类型，返回默认图片
  console.log('图片URL类型不是字符串，使用默认图片');
  return require('@/assets/img/BUSINESS/menus/img.png');
}

// 处理价格显示
function formatPrice(price) {
  console.log('处理价格:', price, typeof price);
  
  if (price === undefined || price === null) {
    console.log('价格为空，返回0.00');
    return '0.00';
  }
  
  // 确保price是数字
  const numPrice = Number(price);
  if (isNaN(numPrice)) {
    console.log('价格不是数字，返回0.00');
    return '0.00';
  }
  
  return numPrice.toFixed(2);
}

// 添加清空购物车的方法
const clearCart = () => {
  cart.value = {};
  saveCartToLocalStorage();
  ElMessage.success('购物车已清空');
};

// 切换购物车显示状态
const toggleCart = () => {
  if (totalQuantity.value > 0) {
    showCart.value = !showCart.value;
  }
};

// 添加减少商品数量的函数
const decrementQuantity = (foodId) => {
  if (cart.value[foodId]) {
    if (cart.value[foodId].quantity > 1) {
      cart.value[foodId].quantity -= 1;
    } else {
      delete cart.value[foodId];
    }
    
    // 更新本地存储中的购物车信息
    saveCartToLocalStorage();
  }
};
</script>

<style scoped>
/* 美化商品列表中的数量控制按钮 */
.cart-actions {
  display: flex;
  align-items: center;
}

.cart-btn {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0;
}

.minus-btn {
  background-color: white;
  border: 1px solid #ff5722;
  color: #ff5722;
}

.plus-btn {
  background-color: #ff5722;
  color: white;
}

.cart-quantity {
  margin: 0 8px;
  font-size: 14px;
  min-width: 20px;
  text-align: center;
}

/**购物车footer部分*/
.footer{
  width: 100%;
  height: 14vw;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  z-index: 100;
}

.footer .cart-left{
  flex: 2;
  background-color:white;
  display: flex;
  align-items: center;
}

.footer .cart-left .cart-left-icon{
  width: 16vw;
  height: 16vw;
  box-sizing: border-box;
  border: 1.6vw solid white;
  border-radius: 8vw;
  background-color: red;
  font-size: 7vw;
  color: white;

  display: flex;
  justify-content: center;
  align-items: center;

  margin-top: -4vw;
  margin-left: 3vw;
  position: relative;/*绝对定位*/
}

.footer .cart-left .cart-left-icon-quantity{
  width: 5vw;
  height: 5vw;
  border-radius: 2.5vw;
  background-color: #17BAF9;
  color: black;
  font-size: 3.6vw;

  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  left : 1.5vw; /* 调整为正值 */
  top: -1.5vw; /* 调整为正值 */
}

.footer .cart-left .cart-price{
  font-size: 4.5vw;
  color: black;
  margin:1vw 1vw 1vw 1vw;
}

.footer .cart-left .cart-empty{
  font-size: 3.8vw;
  color: #AAA;
  margin-top: 4vw;
  margin-left: 3vw;
}

.footer .cart-right{
  flex:1;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

/*够起送费时*/
.footer .cart-right .cart-right-item{
  background-color: #ff4a25;
  width: 78%;
  height: 60%;
  border: 1px solid red;
  border-radius: 20px;
  color: white;
  font-weight: 700;
  user-select: none;
  cursor: pointer;

  display: flex;
  justify-content: center;
  align-items: center;
}

.footer .cart-right .cart-right-item.active{
  background-color: #ff4a25;
}

/* 不够起送费时 */
.footer .cart-right .cart-right-item:not(.active) {
  background-color: #aaa;
  cursor: not-allowed;
}

/* 购物车弹出层样式 */
.cart-container {
  position: fixed;
  bottom: 0; /* 改为0，直接贴合底部 */
  left: 0;
  width: 100%;
  z-index: 999; /* 确保高于footer的z-index */
}

.cart-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: -1;
}

.cart-content {
  background-color: white;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  max-height: 70vh; /* 增加高度以确保覆盖底部 */
  overflow-y: auto;
  animation: slideUp 0.3s ease;
  padding-bottom: 0; /* 移除内边距，使用额外的元素代替 */
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.cart-header h3 {
  margin: 0;
  color: #333;
}

.cart-clear {
  color: #ff5722;
  cursor: pointer;
  font-size: 14px;
}

.cart-clear i {
  margin-right: 5px;
}

.cart-items {
  padding: 0 15px;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f5f5f5;
}

.cart-item-info {
  display: flex;
  align-items: center;
  flex: 1;
}

.cart-item-img {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  object-fit: cover;
  margin-right: 10px;
}

.cart-item-name {
  font-size: 14px;
  color: #333;
  flex: 1;
}

.cart-item-price {
  font-size: 14px;
  color: #ff5722;
  font-weight: bold;
  margin-right: 15px;
}

.quantity-controls {
  display: flex;
  align-items: center;
}

.quantity-btn {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  border: 1px solid #eee;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0;
  transition: all 0.2s ease;
}

.quantity-btn.minus {
  color: #999;
  border-color: #ddd;
}

.quantity-btn.plus {
  color: white;
  background-color: #ff5722;
  border-color: #ff5722;
}

.quantity-value {
  margin: 0 8px;
  font-size: 14px;
  min-width: 20px;
  text-align: center;
}

.cart-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #f9f9f9;
  border-top: 1px solid #eee;
  position: sticky;
  bottom: 0;
  z-index: 2;
}

/* 添加底部填充区域样式 */
.cart-bottom-padding {
  height: 14vw; /* 与footer高度相同 */
  width: 100%;
  background-color: #f9f9f9;
}

.total {
  font-size: 16px;
  font-weight: bold;
  color: #ff5722;
}

.checkout-btn {
  background-color: #ff5722;
  color: white;
  border: none;
  padding: 8px 20px;
  border-radius: 20px;
  font-weight: bold;
  cursor: pointer;
}

/* 原有样式保留 */
.wrapper {
  width: 100%;
  height: 100%;
  padding-bottom: 60px; /* 为底部购物车留出空间 */
}

.wrapper .header .title {
  width: 430px;
  height: 160px;
  position: relative;
}

.wrapper .header .title .img2{
  width: 100%;
  height: 60%;
}

.wrapper .header .title .img1{
  position: relative;
  z-index: 1;
  top: 108px;
  left: 18px;
  width: 90%;
}

.wrapper .header .header-all{
  width: 92%;
  display: flex;
  margin-left: 3.5vw;
  margin-top: -3vw;
  justify-content: space-between;
  border: 1px solid red;
  border-radius: 10px;
}

.wrapper .header .header-all .header-text{
  flex: 7;
  border-radius: 10px;
  width: 100%;
  box-sizing: border-box;
  padding-left: 3vw;
}

.wrapper .header .header-all .header-text .text-left-header {
  display: flex;
  align-items: center;
  margin-bottom: 2vw;
}

.wrapper .header .header-all .header-text .text-left-header h{
  color: black;
  font-size:5vw;
  font-weight: bolder;
}

.wrapper .header .header-all .header-text .star{
  display: flex;
  font-size: 2.7vw;
  align-items: center;
  margin-bottom: 2vw;
}

.wrapper .header .header-all .header-text .star p1{
  color: #ff4a25;
  font-weight: bolder;
}

.wrapper .header .header-all .header-text .star p2{
  color:#17BAF9;
  padding:0 1vw 0 1vw;
  border-radius: 5px;
  border: 1px solid #17BAF9;
  margin: 0 2vw 0 2vw;
}

.wrapper .header .header-all .header-text .star p3{
  color: #999999;
}

.wrapper .header .header-all .header-text .for-sell{
  display: flex;
  align-items: center;
  font-size: 2.7vw;
  margin-bottom: 3vw;
}

.wrapper .header .header-all .header-text .for-sell p1{
  border: 1px solid #ff8b39;
  color:#ff8b39 ;
  padding-left: 1vw;
  padding-right: 1vw;
  border-radius: 5px;
}

.wrapper .header .header-all .header-text .for-sell p2{
  margin-left: 3vw;
  border: 1px solid red;
  color:red;
  padding-left: 1vw;
  padding-right: 1vw;
  border-radius: 5px;
}

.wrapper .header .header-all .text-picture {
  flex: 3;
}

.wrapper .header .header-all .text-picture img{
  width: 13vw;
  height: 13vw;
  margin-left: 10vw;
}
.wrapper .header .banner{
  display: flex;
  align-items: center;
  border: 1px red solid;
  border-radius: 5px;
  margin:2.5vw 4vw 0 3.5vw;
  padding: 2vw 2vw 2vw 2vw ;
  font-size: 3vw;
  color:chocolate;
}

.wrapper .header .banner p2{
  padding-left: 10vw;
}

/*主体点餐*/
.wrapper .body .picture{
  display: flex;
  align-items: center;
}
.wrapper .body .picture img{
  width: 95%;
  height: 50%;
  margin: 0 auto;
}
.wrapper .body .recommend{
  display: flex;
  margin: 2vw 4.5vw 2vw 0vw;
  padding: 1vw 0 1vw 0;
}
.wrapper .body .recommend .recommend-left{
  display: flex;
  flex:7;
  margin-right: 10vw;
  justify-content:space-around;
  align-items: center;
}

.wrapper .body .recommend .recommend-right{
  flex: 3;
  border: 1px solid red;
  justify-content: space-around;
  align-items: center;
  border-radius: 10px;
}

.wrapper .body .recommend .recommend-right p{
  margin: 1vw 1vw 1vw 7vw;
  font-size: 3vw;
  color: red;
}

.wrapper .body .foodList{
  display: flex;
}

.wrapper .body .foodList .foodList-left{
  flex: 2;
  position: sticky;
  top: 0;
  background-color: aliceblue;
}

.wrapper .body .foodList .foodList-left .title0{
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 2vw;
}

.wrapper .body .foodList .foodList-left .title0 p{
  margin-left: 1vw;
  margin-right: 4vw;
  font-size: 3vw;
}
.wrapper .body .foodList .foodList-left .title1{
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 4vw;
}

.wrapper .body .foodList .foodList-left .title1 p{
  margin-left: 1vw;
  margin-right: 4vw;
  font-size: 3vw;
}

.wrapper .body .foodList .foodList-left .title2{
  margin-left: 3vw;
  margin-bottom: 7vw;
}

.wrapper .body .foodList .foodList-left .title2 p{
  font-size: 3vw;
  margin-bottom: 0;
}

.wrapper .body .foodList .foodList-right{
  flex: 8;
  margin-left: 2vw;
  margin-top: 2vw;
  margin-right: 4vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-title{
  display: flex;
  margin-top: 2vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-title p1{
  font-weight: bolder;
  margin:0 2vw 3vw 2vw ;
  font-size: 3vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-title p2{
  font-size: 2.7vw;
  color: #9F9F9F;
}

.wrapper .body .foodList .foodList-right .menus .menus-body{
  display: flex;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-left{
  flex: 3;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-left img{
  width: 110%;
  height: 90%;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right{
  flex: 7;
  margin-left: 2vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-title{
  font-weight: bolder;
  margin-bottom: 1vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-banner{
  font-size: 2.9vw;
  margin-bottom: 1vw;
  color: #9F9F9F;
}
.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-count{
  font-size: 2.9vw;
  margin-bottom: 1vw;
  color: #9F9F9F;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-sell{
  display: flex;
  font-size: 2.6vw;
  color: #ff4a25;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-sell p4{
  border: 1px solid #ff4a25;
  border-radius: 5px;
  padding: 0.5vw 1vw 0.5vw 1vw;
  margin-right: 2vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-sell p5{
  border: 1px solid #ff4a25;
  border-radius: 5px;
  padding: 0.5vw 1vw 0.5vw 1vw;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-price{
  display: flex;
  justify-content:space-between;
  align-items: center;
}
.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-price p {
  font-weight: bolder;
  color: red;
}
</style>