<template>
  <div class="register-container">
    <div class="register-card">
      <div class="logo-container">
        <img :src="require('@/assets/elm_title.gif')" class="logo-img" alt="饿了么" />
    </div>
      
      <h2 class="welcome-text">创建新账号</h2>
      
      <el-form @submit.prevent="register" class="register-form">
        <el-form-item>
          <el-input 
            v-model="phoneNumber" 
            placeholder="请输入手机号码" 
            prefix-icon="el-icon-mobile-phone"
            class="custom-input">
          </el-input>
    </el-form-item>
        
        <el-form-item>
          <el-input 
            type="password" 
            v-model="password" 
            placeholder="请输入密码" 
            prefix-icon="el-icon-lock"
            class="custom-input"
            show-password>
          </el-input>
    </el-form-item>
        
        <el-form-item>
          <el-input 
            type="password" 
            v-model="confirmPassword" 
            placeholder="请确认密码" 
            prefix-icon="el-icon-lock"
            class="custom-input"
            show-password>
          </el-input>
    </el-form-item>
        
        <div class="button-container">
          <el-button 
            type="primary" 
            @click="register" 
            class="register-button"
            :loading="loading">
            立即注册
          </el-button>
        </div>
        
        <div class="login-link">
          已有账号？ 
          <span @click="goToLogin" class="login-text">返回登录</span>
        </div>
      </el-form>
    </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { userApi } from '@/utils/api';

const router = useRouter();
const phoneNumber = ref('');
const password = ref('');
const confirmPassword = ref('');
const loading = ref(false);

const register = async () => {
  try {
    if (!phoneNumber.value) {
      ElMessage.error('请输入手机号码');
      return;
    }
    
    if (!password.value) {
      ElMessage.error('请输入密码');
      return;
    }
    
    if (password.value !== confirmPassword.value) {
      ElMessage.error('两次输入的密码不一致');
      return;
    }
    
    loading.value = true;
    
    const response = await userApi.register({
      userId: phoneNumber.value,
      password: password.value,
      // 可以添加其他需要的字段
    });
    
    if (response && response.success) {
      ElMessage.success('注册成功');
      router.push('/login-component');
    } else {
      ElMessage.error('注册失败：' + (response?.message || '未知错误'));
    }
  } catch (error) {
    console.error('注册失败:', error);
    ElMessage.error('注册失败：' + (error.message || '服务器错误'));
  } finally {
    loading.value = false;
  }
};

const goToLogin = () => {
  router.push('/login-component');
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.register-card {
  width: 100%;
  max-width: 400px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  padding: 30px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.register-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
}

.logo-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-img {
  height: 80px;
  max-width: 100%;
  object-fit: contain;
}

.welcome-text {
  text-align: center;
  color: #333;
  font-size: 24px;
  margin-bottom: 30px;
  font-weight: 500;
}

.register-form {
  width: 100%;
}

.custom-input :deep(.el-input__inner) {
  height: 50px;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
  padding-left: 15px;
}

.custom-input :deep(.el-input__inner:focus) {
  border-color: #409EFF;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.button-container {
  margin-top: 20px;
  margin-bottom: 20px;
}

.register-button {
  width: 100%;
  height: 50px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(90deg, #0096ff 0%, #0081ff 100%);
  border: none;
  transition: all 0.3s;
}

.register-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 129, 255, 0.3);
}

.login-link {
  text-align: center;
  margin-top: 20px;
  color: #606266;
  font-size: 14px;
}

.login-text {
  color: #0081ff;
  cursor: pointer;
  font-weight: 500;
  transition: color 0.3s;
}

.login-text:hover {
  color: #409EFF;
  text-decoration: underline;
}

@media (max-width: 480px) {
  .register-card {
    padding: 20px;
}

  .welcome-text {
    font-size: 20px;
    margin-bottom: 20px;
  }
  
  .custom-input :deep(.el-input__inner) {
    height: 45px;
  }
  
  .register-button {
    height: 45px;
  }
}
</style>