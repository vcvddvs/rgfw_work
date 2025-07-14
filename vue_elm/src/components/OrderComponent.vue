<template>
  <div class="wrapper">
    <header class="header">
      <div class="title">
        <div class="p1">
                    <!-- 使用 Element Plus 的返回图标 -->
                    <el-icon @click="goBack" style="background-color:#9F9F9F">
                      <img :src="require('@/assets/img_10.png')" alt="">
                    </el-icon>
        </div>
        <div class="p2">
          <p>确认订单</p>
        </div>
      </div>
      <div class="banner">
        <span class="iconfont icon-guangbogonggao" style="padding-left: 2vw"></span>
        <p style="padding-left: 2vw">温馨提示： 请适量点餐,避免浪费</p>
      </div>
      <div class="location">
        <div class="location-text">
          <p>地址: {{ selectedAddress }}</p>
          <img :src="require('@/assets/img/orders/img.png')"
               height="23"
               width="17" alt="描述"
               @click="goToAddressPage " />
        </div>
        <!-- 显示地址列表 -->
        <div v-if="showAddresses" class="address-list">
          <div
              v-for="address in deliveryAddresses"
              :key="address.daId"
              class="address-item"
              @click="selectAddress(address)"
          >
            {{  address.address }}
          </div>
        </div>
        <div class="location-speed">
          <p>立即送出</p>
          <div class="location-speed-to">
            <p>预计13:40送达</p>
            <img :src="require('@/assets/img/orders/img.png')" height="23" width="17" />
          </div>
        </div>
      </div>
    </header>
    <main class="body">
      <div class="body-title">
        <div class="title1">
          <p>{{ businessName }}</p>

        </div>
        <div class="title2">
          <p>蜂鸟准时达</p>
        </div>
      </div>
      <div class="body-text">
        <div v-for="item in foodItems" :key="item.title" class="food">
          <div class="food-left">
            <img :src="item.photo" alt="商品图片" />
          </div>
          <div class="food-right">
            <div class="food-right-title">
              <div class="title1">
                <p>{{ item.title }}</p>
              </div>
              <div class="price">
                <p>&#165;{{ item.price }}</p>
              </div>
            </div>
            <div class="name">
              <p>{{ item.description }}</p>
            </div>
            <div class="number">
              <p>x {{ item.quantity }}</p>
            </div>
          </div>
        </div>

        <!-- 其他费用和总计 -->
      </div>
    </main>
    <footer class="footer">
      <div class="foot-left">
        <div class="sum-price">
          <p1>合计</p1>
          <p2>{{ totalAmount.toFixed(2) }}</p2> <!-- 格式化金额显示 -->
        </div>
      </div>
      <div class="foot-right" @click="submitOrder">
        <p>提交订单</p>
      </div>
    </footer>
  </div>
</template>



<script setup>
import {ref, computed, onMounted, watch} from 'vue';
import { useRoute } from 'vue-router';
import router from "@/router";

const route = useRoute();
const businessName = ref(route.params.businessName || '默认店铺名称');
const businessId = ref(route.params.businessId || 0);
const deliveryAddresses = ref([]);
const showAddresses = ref(false);

// 从 localStorage 获取选中的地址
const selectedAddress = ref(JSON.parse(localStorage.getItem('selectedAddress') || '{}').address || '云南大学楸苑1栋');

// 从 localStorage 获取订单信息
const orderInfo = JSON.parse(localStorage.getItem('orderInfo') || '{}');
const cart = ref({});

// 初始化购物车数据
if (orderInfo.cart) {
  cart.value = orderInfo.cart;
  businessId.value = orderInfo.businessId;
  businessName.value = orderInfo.businessName;
}

onMounted(() => {
  // 如果购物车中的商品没有图片，尝试从缓存中获取
  Object.values(cart.value).forEach(item => {
    if (!item.foodImg) {
      console.log('商品缺少图片:', item.foodName);
      // 可以在这里添加逻辑，根据foodId从后端获取图片
    }
  });
  
  // 获取配送地址列表
  fetchDeliveryAddresses();
});

// 监听路由变化，更新订单信息
watch(route, (newRoute) => {
  if (newRoute.name === 'Order') {
    const newOrderInfo = JSON.parse(localStorage.getItem('orderInfo') || '{}');
    if (newOrderInfo.cart) {
      cart.value = newOrderInfo.cart;
      businessId.value = newOrderInfo.businessId;
      businessName.value = newOrderInfo.businessName;
    }
  }
});

if (route.query.cart) {
  try {
    cart.value = JSON.parse(route.query.cart);
  } catch (error) {
    console.error('解析购物车数据失败:', error);
    cart.value = {}; // 确保在解析失败时设置为空对象
  }
}

const goToAddressPage = () => {
  // 为了测试，我们使用固定的userId，这个userId在数据库中存在地址记录
  // 从截图可以看到，数据库中有地址记录的userId有: 1111111111, 1234567111, 1234567222, 15758096290
  const testUserId = '15758096290'; // 使用数据库中存在的userId
  
  const userId = localStorage.getItem('userId'); // 从localStorage获取userId
  if (!userId) {
    console.error('用户未登录');
    return;
  }
  
  console.log('跳转到地址页面，使用userId:', userId);
  console.log('提示: 如果没有显示地址，请尝试使用数据库中存在的userId:', testUserId);
  
  // 导航到 address 组件，并传递 userId
  router.push({
    name: 'Address',
    params: { 
      userId: userId,  // 正常情况下使用登录用户的ID
      // userId: testUserId, // 测试时可以使用这行替换上面的行
      businessId: businessId.value, 
      businessName: businessName.value 
    }
  });
};

// 获取配送地址列表
const fetchDeliveryAddresses = () => {
  const userId = localStorage.getItem('userId');
  if (!userId) {
    console.error('用户未登录，无法获取配送地址');
    return;
  }
  
  // 使用API获取配送地址
  import('@/utils/api').then(({ deliveryAddressApi }) => {
    deliveryAddressApi.getDeliveryAddressesByUserId(userId)
      .then(response => {
        if (response && response.code === 1 && response.data) {
          deliveryAddresses.value = response.data;
          // 默认选中第一个地址
          if (response.data.length > 0) {
            selectedAddress.value = response.data[0].address;
          }
        } else {
          console.error('获取配送地址失败:', response);
        }
      })
      .catch(error => {
        console.error('获取配送地址出错:', error);
      });
  });
};

// 选择配送地址
const selectAddress = (address) => {
  selectedAddress.value = address.address;
  showAddresses.value = false;
};

onMounted(() => {
  showAddresses.value = false;
});

const foodItems = computed(() => {
  return Object.values(cart.value).map(item => ({
    title: item.foodName || '未命名菜品',
    price: item.price,
    description: item.foodDescription || item.description || '', // 优先使用保存的描述
    quantity: item.quantity,
    foodId: item.foodId,
    businessId: item.businessId || businessId.value,
    businessName: item.businessName || businessName.value,
    businessImg: item.businessImg || '',
    photo: processImageUrl(item.foodImg) // 使用函数处理图片路径
  }));
});

// 处理图片URL，确保能正确显示
const processImageUrl = (url) => {
  if (!url) {
    return require('@/assets/img/BUSINESS/menus/img.png');
  }
  
  // 如果是完整URL或base64，直接返回
  if (url.startsWith('http') || url.startsWith('data:')) {
    return url;
  }
  
  // 如果是相对路径，添加baseURL
  if (url.startsWith('/')) {
    return 'http://localhost:8090' + url;
  }
  
  // 尝试加载项目中的图片
  try {
    // 首先尝试从BUSINESS目录加载
    if (!url.includes('/')) {
      try {
        return require('@/assets/img/BUSINESS/' + url);
      } catch (e) {
        console.log('BUSINESS目录未找到图片，尝试其他路径');
      }
    }
    
    // 然后尝试从普通img目录加载
    return require('@/assets/img/' + url);
  } catch (e) {
    console.error('无法加载图片:', url, e);
    return require('@/assets/img/BUSINESS/menus/img.png');
  }
};

const totalAmount = computed(() => {
  return Object.values(cart.value).reduce((sum, item) => sum + item.quantity * item.price, 0);
});

const submitOrder = () => {
  if (Object.keys(cart.value).length === 0) {
    alert('购物车为空，请先添加商品！');
    return;
  }

  const userId = localStorage.getItem('userId');
  if (!userId) {
    alert('用户未登录，请先登录！');
    return;
  }

  const orderDate = new Date().toISOString().slice(0, 10); // 订单日期
  const orderTotal = totalAmount.value; // 订单总价
  const daIdSelected = orderInfo.selectedDaId; // 使用选中的 daId

  // 构建订单信息，包含商家详细信息
  const order = {
    userId: userId,
    businessId: businessId.value,
    businessName: businessName.value,
    businessImg: orderInfo.businessImg || '',
    orderDate: orderDate,
    orderTotal: orderTotal,
    daId: daIdSelected,
    orderState: 0 // 未支付
  };

  // 构建订单详情，包含商品详细信息
  const details = Object.values(cart.value).map(item => ({
    foodId: item.foodId,
    foodName: item.foodName,
    foodImg: item.foodImg || '',
    price: item.price,
    quantity: item.quantity,
    remarks: item.remarks || ''
  }));

  // 保存订单信息到本地存储，以便在支付页面和历史订单页面使用
  const orderData = {
    order: order,
    details: details,
    timestamp: new Date().getTime()
  };
  localStorage.setItem('currentOrder', JSON.stringify(orderData));

  fetch('http://localhost:8080/elm_servlet_war_exploded/api/orders/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ order, details }), // 发送包含 order 和 details 的请求体
  })
  .then(response => {
    if (response.ok) {
      return response.text();
    } else {
      throw new Error('Network response was not ok.');
    }
  })
  .then(data => {
    console.log('Order submitted successfully:', data);
    // 清空购物车
    localStorage.removeItem('cartInfo');
    // 跳转到支付页面
    router.push({
      name: 'Payment',
      query: { 
        totalAmount: totalAmount.value, 
        businessName: businessName.value,
        businessId: businessId.value,
        businessImg: orderInfo.businessImg || '',
        orderId: data.orderId || 'pending' // 如果后端返回了订单ID，则使用它
      }
    });
  })
  .catch((error) => {
    console.error('Error:', error);
    // 即使出错，也跳转到支付页面，因为我们已经保存了订单信息
    router.push({
      name: 'Payment',
      query: { 
        totalAmount: totalAmount.value, 
        businessName: businessName.value,
        businessId: businessId.value,
        businessImg: orderInfo.businessImg || '',
        orderId: 'pending'
      }
    });
  });
};

const goBack = () => {
  // 返回到商家页面，需要带上完整参数
  router.push({
    name: 'BusinessList2', 
    params: {
      businessId: businessId.value,
      businessName: businessName.value,
      businessImg: orderInfo.businessImg || 'default.jpg' // 使用存储的商家图片或默认图片
    }
  });
};
</script>



<style scoped>
/* 添加 CSS 样式 */

.wrapper {
  width: 100%;
  height: 100%;
}

.wrapper .header .title {
  display: flex;
  align-items: center;
  background-color: red;
  text-align: center;
  height: 18vw;


}

.wrapper .header .title .p1 {
  flex: 1;
  color: white;
}

.el-icon {
  cursor: pointer; /* 添加鼠标手型图标 */
  font-size: 1.5em; /* 调整图标大小 */
  color: white; /* 设置图标颜色 */
}

.wrapper .header .title .p2 {
  flex: 9;
  color: white;
  font-size: 4.5vw;
  padding-right: 6vw;
}

.wrapper .header .banner{
  display: flex;
  font-size: 2.9vw;
  align-items: center;
  border: none;
  background-color: #ffe99a;
  color: coral;
  border-radius: 10px;
  width: 93%;
  height: 33px;
  margin: 0 auto;
  margin-top: 2vw;

}

.wrapper .header .location{
  border: 1px solid red;
  width: 93%;
  margin: 0 auto;
  margin-top: 2vw;

  border-radius: 10px;
}

.address-list {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  width:85%;
  z-index: 100;
  margin-top: 5px;
  margin-left: 5px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.2);
}

.address-item {
  padding: 10px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.address-item:last-child {
  border-bottom: none;
}

.wrapper .header .location-text{
  display: flex;
  align-items: center;
  justify-content: space-between;

  font-size: 5vw;
  font-weight: bolder;

  margin: 0 2vw 0 2vw;
}

.wrapper .header .location-speed{
  display: flex;
  align-items: center;
  justify-content: space-between;

  font-weight: bolder;

  margin: 0 2vw 0 2vw;
}

.wrapper .header .location-speed .location-speed-to{
  display: flex;
  align-items: center;
  color: red;
}

/*主体部分*/
.wrapper .body{
  border: 1px solid red;
  width: 93%;
  margin: 0 auto;
  margin-top: 2vw;
  border-radius: 10px;
}

.wrapper .body .body-title{
  display: flex;
  justify-content: space-between;
  padding: 0 2vw 0 2vw;
  align-items: center;
}

.wrapper .body .body-title .title1{
  color: #999999;
  font-size: 4vw;
}

.wrapper .body .body-title .title2 p{
  color: #17BAF9;
  border: 1px solid #b3d4fc;
  border-radius: 4px;
  font-size: 3vw;
  padding: 0.5vw 0.5vw 0.5vw 0.5vw;
}


.wrapper .body .body-text .food {

  display: flex;
  align-items: center;

}

.wrapper .body .body-text .food .food-left{
  flex: 2;
}

.wrapper .body .body-text .food .food-left img{
  width: 75px;

}

.wrapper .body .body-text .food .food-right{
  flex: 8;
}
.wrapper .body .body-text .food .food-right .food-right-title{
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 20px;
  font-size: 3.6vw;
  font-weight: bolder;
  padding: 0 1vw 0 1vw;
}

.wrapper .body .body-text .food .food-right .name{
  font-size: 2.8vw;
  color: #9F9F9F;
}

.wrapper .body .body-text .food .food-right .number{
  height: 18px;
  color: #9F9F9F;
}

.wrapper .body .db-price{
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 3.5vw;
  padding: 0 2vw 0 2vw;
  margin-bottom: 1vw;
}

.wrapper .body .ps-price{
  padding: 0 2vw 0 2vw;
}
.wrapper .body .ps-price .price1{
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 10px;
}

.wrapper .body .ps-price .price1 .j-psf{
  display: flex;
  align-items: center;
  font-size: 3.5vw;
}

.wrapper .body .ps-price .price1 .j-psf img{
  width: 15px;
  height: 15px;
}

.wrapper .body .ps-price .price2{
  font-size: 3vw;
  height: 10px;
  color: red;
}

.wrapper .body .red-bag{
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 2vw 0 2vw;
}

.wrapper .body .red-bag-tp{
  display: flex;
  align-items: center;
  font-size: 3.5vw;
}

.wrapper .body .red-bag-tp img{
  width: 15px;
  height: 15px;
}

.wrapper .body .red-bag-kx{
  display: flex;
  align-items: center;
  font-size: 3vw;
  color: #ff4a25;
  border: none;
  border-radius: 10px;
  background-color: #e7e1cd;
}

.wrapper .body .red-bag-kx p{
  margin: 0 1vw 0 2vw;
}

.wrapper .body .xd-fd{
  display: flex;
  align-items: center;
  font-size: 3.5vw;
  justify-content: space-between;
  padding: 0 2vw 0 2vw;
}

.wrapper .body .xd-fd p{
  color: #ff4a25;
}

.wrapper .body .xd-fd .fd-tp{
  display: flex;
  align-items: center;
}

.wrapper .body .xd-fd .fd-tp img{
  width: 15px;
  height: 15px;
}

.wrapper .body .yh-sm{
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 3vw 2vw 0 2vw;
  border-top: 1px solid #9F9F9F;

}

.wrapper .body .yh-sm p1{
  font-size: 3vw;
  color: #9F9F9F;
}

.wrapper .body .yh-xj{
  display: flex;
  align-items: center;
}

.wrapper .body .yh-xj p2{
  font-size: 4vw;
}

.wrapper .body .yh-xj p3{
  font-size: 6.5vw;
  font-weight: bolder;
}
/*底部说明*/
.wrapper .footer {
  width: 100%;
  height: 14vw;
  border-top: solid 1px #DDD;
  background-color: white;
  position: fixed;
  left: 0;
  bottom: 0;

  display: flex;
  align-items: center;
}

.wrapper .footer .foot-left{
  flex: 7;
  display: flex;
  justify-content: flex-end;
}

.wrapper .footer .foot-left .sum-price p1{
  font-size: 2.7vw;
  font-weight: bolder;
}

.wrapper .footer .foot-left .sum-price p2{
  font-size: 6vw;
  font-weight: bolder;
}


.wrapper .footer .foot-right{
  flex:3;
  display: flex;
  justify-content: center;
}
.wrapper .footer .foot-right p{
  border: 1px solid red;
  padding: 2vw 4vw 2vw 4vw;
  background-color: red;
  color: white;
  border-radius: 15px;
  font-size: 3.5vw;
  font-weight: bolder;

}



.wrapper .body .back-bored{
  margin-top: 3vw;
}
</style>
