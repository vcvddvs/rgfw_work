
<template>
  <div class="wrapper">
    <div class="header">
      <h class="title">选择收货地址</h>
      <div class="img">
        <img :src="require('@/assets/img_5.png')" alt="">
      </div>
    </div>
    <div>
      <img :src="require('@/assets/img_6.png')" style="width: 100%; margin-top: 10px" alt="">
    </div>
    <div v-if="deliveryAddresses.length === 0" class="text_title">
      <h1>加载中...</h1>
    </div>
    <div v-for="(address, index) in deliveryAddresses" :key="index" class="address-item">
      <div class="body">
        <div class="tb" @click="selectAddress(address)">
          <div class="text">
            <div class="text_title">
              <h1 style="font-size: 10px; color: #17BAF9; border: #b3d4fc 1px solid; background-color: #b3d4fc; border-radius: 20%;">学校</h1>
              <h2 style="font-size: 16px; margin-left: 10px; font-weight: bolder">{{ address.address }}</h2>
            </div>
            <div class="text_body">
              <h1>姓名: {{ address.contactName }}</h1>
              <h2>电话: {{ address.contactTel }}</h2>
            </div>
          </div>
        </div>
        <div class="xg" style="margin-right: 10px">
          <img :src="require('@/assets/img_8.png')" style="width: 30px" @click="confirmDeleteAddress(address, index)">
        </div>
      </div>
    </div>
    <div class="footer">
      <img :src="require('@/assets/img_9.png')"
           style="width: 100%;
           margin-top: 10px" @click="editAddress()">
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import router from "@/router";
import { deliveryAddressApi } from '@/utils/api';

const route = useRoute();
const userId = ref(route.params.userId);
const businessId = ref(route.params.businessId);
const businessName = ref(route.params.businessName);
const deliveryAddresses = ref([]);

const fetchDeliveryAddresses = async () => {
  if (!userId.value) {
    console.error('用户ID未提供');
    return;
  }
  try {
    console.log('正在获取用户地址，userId:', userId.value);
    const response = await deliveryAddressApi.getDeliveryAddressesByUserId(userId.value);
    console.log('获取地址响应:', response);
    
    // 检查响应格式，适应不同的后端返回结构
    if (response && response.data) {
      // 如果响应是标准的Result格式
      deliveryAddresses.value = response.data;
    } else if (Array.isArray(response)) {
      // 如果响应直接是数组
      deliveryAddresses.value = response;
    } else {
      console.error('无法解析地址响应:', response);
      deliveryAddresses.value = [];
    }
  } catch (error) {
    console.error('获取地址失败:', error);
    deliveryAddresses.value = []; // 确保在错误时设置为空数组
  }
};

onMounted(fetchDeliveryAddresses);

const selectAddress = (address) => {
  const isConfirmed = window.confirm('确定选择这个地址吗？');
  if (isConfirmed) {
    chooseAddress(address);
  } else {
    console.log('选择地址已取消');
  }
};

const chooseAddress = (address) => {
  localStorage.setItem('selectedAddress', JSON.stringify(address));

  // 保存订单信息到 localStorage
  const orderInfo = JSON.parse(localStorage.getItem('orderInfo') || '{}');
  orderInfo.selectedAddress = address;
  orderInfo.selectedDaId = address.daId; // 保存 daId
  localStorage.setItem('orderInfo', JSON.stringify(orderInfo));

  router.push({
    name: 'Order',
    params: { businessId: businessId.value, businessName: businessName.value }
  });
};

const confirmDeleteAddress = (address, index) => {
  const isConfirmed = window.confirm('确定删除这个地址吗？');
  if (isConfirmed) {
    deleteAddress(address, index);
  } else {
    console.log('删除地址已取消');
  }
};

const deleteAddress = async (address, index) => {
  try {
    const response = await deliveryAddressApi.deleteDeliveryAddress(address.daId);
    console.log('删除地址响应:', response);
    
    // 检查响应格式，适应不同的后端返回结构
    let success = false;
    if (response && response.code === 1) {
      success = true;
    } else if (response === true) {
      success = true;
    }
    
    if (success) {
      deliveryAddresses.value.splice(index, 1);
      alert('地址删除成功');
    } else {
      alert('地址删除失败');
    }
  } catch (error) {
    console.error('删除地址失败:', error);
    alert('地址删除失败');
  }
};

const editAddress = () => {
  router.push({
    name: 'Updata',
    params: { userId: userId.value }
  });
};
</script>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header h {
  flex-grow: 1;
  text-align: center;
  font-weight: bolder;
}

.img img {
  width: 70%;
}

.body {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  border-bottom: 1px solid #999999; /* 添加底部线条 */
  padding-bottom: 10px; /* 根据需要调整间距 */
}

.tb {
  display: flex;
}

.text {
  display: flex;
  flex-direction: column;
}

.text_title {
  display: flex;
  justify-content: center;
  align-items: center;
}

.text_title h1 {
  font-weight: normal;
  margin-left: 10px;
}

.text_body {
  display: flex;
  align-items: center;
  color: #999999;
}

.text_body h1,
.text_body h2 {
  font-weight: normal;
  font-size: 10px;
  margin-left: 10px;
}

.xg {
  margin-right: 10px;
}

.xg img {
  width: 30px;
}

.footer img {
  width: 100%;
}
</style>

