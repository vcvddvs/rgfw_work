<template>
  <div class="edit-address">
    <h2>添加新地址</h2>
    <!-- 表单用于编辑地址 -->
    <el-form ref="editAddressForm" :model="address" label-width="100px">
      <el-form-item label="地址" required>
        <el-input v-model="address.address" placeholder="请输入详细地址"></el-input>
      </el-form-item>
      <el-form-item label="联系人姓名" required>
        <el-input v-model="address.contactName" placeholder="请输入联系人姓名"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" required>
        <el-input v-model="address.contactTel" placeholder="请输入联系电话"></el-input>
      </el-form-item>
      <el-button type="primary" @click="addAddress" :loading="loading" style="width: 100%;">保存</el-button>
      <el-button @click="goBack" style="width: 100%; margin-top: 10px;">返回</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import router from "@/router";
import { deliveryAddressApi } from '@/utils/api';
import { ElMessage } from 'element-plus';

const route = useRoute();
const userId = ref(route.params.userId);
const address = ref({});
const loading = ref(false);

onMounted(async () => {
  // 从 localStorage 获取要编辑的地址
  const selectedAddress = JSON.parse(localStorage.getItem('selectedAddressForEdit'));
  if (selectedAddress) {
    address.value = selectedAddress;
  } else {
    // 初始化新地址
    address.value = {
      contactName: '',
      contactSex: 1, // 默认值
      contactTel: '',
      address: '',
      userId: userId.value
    };
  }
  console.log("初始化地址信息:", address.value);
});

const validateForm = () => {
  if (!address.value.address || address.value.address.trim() === '') {
    ElMessage.error('请输入详细地址');
    return false;
  }
  if (!address.value.contactName || address.value.contactName.trim() === '') {
    ElMessage.error('请输入联系人姓名');
    return false;
  }
  if (!address.value.contactTel || address.value.contactTel.trim() === '') {
    ElMessage.error('请输入联系电话');
    return false;
  }
  return true;
};

const addAddress = async () => {
  if (!validateForm()) return;
  
  try {
    loading.value = true;
    
    // 确保发送请求时 address 对象包含 userId
    const addressData = { ...address.value };
    delete addressData.daId; // 如果是编辑模式可能有daId，新增时需要删除
    addressData.contactSex = addressData.contactSex || 1;
    addressData.userId = userId.value;
    
    console.log("发送地址数据:", addressData);
    
    const response = await deliveryAddressApi.addDeliveryAddress(addressData);
    console.log("添加地址响应:", response);
    
    // 检查响应格式
    let success = false;
    if (response && response.code === 1) {
      success = true;
    } else if (response === true || (response && response.success)) {
      success = true;
    }
    
    if (success) {
      ElMessage.success('地址添加成功');
      router.back(); // 返回上一页
    } else {
      ElMessage.error('地址添加失败: ' + (response?.message || '未知错误'));
    }
  } catch (error) {
    console.error('添加地址失败:', error);
    ElMessage.error('地址添加失败: ' + (error.message || '服务器错误'));
  } finally {
    loading.value = false;
  }
};

const goBack = () => {
  router.back();
};
</script>

<style scoped>
.edit-address {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #409EFF;
}

.el-button {
  margin-top: 20px;
}
</style>