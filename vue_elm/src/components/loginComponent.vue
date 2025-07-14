<template>
  <div class="login-container">
    <div class="login-card">
      <div class="logo-container">
        <img :src="require('@/assets/elm_title.gif')" class="logo-img" alt="饿了么" />
      </div>
      
      <h2 class="welcome-text">欢迎回来</h2>
      
      <el-form @submit.prevent="login" class="login-form">
        <el-form-item>
          <el-input 
            v-model="userId" 
            placeholder="请输入账号" 
            prefix-icon="el-icon-user"
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
        
        <div class="button-container">
          <el-button 
            type="primary" 
            @click="login" 
            class="login-button"
            :loading="loading">
            登录
          </el-button>
        </div>
        
        <div class="register-link">
          还没有账号？ 
          <span @click="register" class="register-text">立即注册</span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';
import { userApi } from '@/utils/api';
import { setToken } from '@/utils/auth';
import axios from 'axios';

const router = useRouter();
const userId = ref('');
const password = ref('');
const loading = ref(false);

const login = async () => {
  try {
    if (!userId.value || !password.value) {
      ElMessage.error('请输入账号和密码');
      return;
    }

    loading.value = true;
    
    // 打印请求数据
    console.log('发送登录请求:', {
      userId: userId.value,
      password: password.value
    });

    const response = await userApi.login({
      userId: userId.value,
      password: password.value
    });

    console.log('登录响应:', response);

    if (response && response.code === 1 && response.data) {
      // 检查响应格式，适应后端返回的不同格式
      let token;
      if (typeof response.data === 'string') {
        token = response.data;
      } else if (response.data.token) {
        token = response.data.token;
      }
      
      // 验证token
      if (typeof token === 'string' && token.length > 0) {
        console.log('获取到token:', token);
        
        // 保存认证信息
        setToken(token);
        localStorage.setItem('userId', response.data.userId || userId.value);
        
        // 验证token是否正确保存
        const savedToken = localStorage.getItem('token');
        console.log('保存的token:', savedToken);
        console.log('Authorization header:', axios.defaults.headers.common['Authorization']);
        
        ElMessage.success('登录成功');
        router.push('/Index-Component');
        return;
      } else {
        console.error('Token无效:', token);
        ElMessage.error('登录失败：无效的认证信息');
      }
    } else {
      // 如果没有在上面返回，说明登录失败
      console.error('登录失败，响应数据:', response);
      ElMessage.error(response?.msg || '登录失败，请检查用户名和密码');
    }
  } catch (error) {
    // 详细的错误日志
    console.error('登录异常:', error);

    if (error.response) {
      switch (error.response.status) {
        case 400:
          ElMessage.error('请求参数错误');
          break;
        case 401:
          ElMessage.error('用户名或密码错误');
          break;
        case 403:
          ElMessage.error('没有访问权限');
          break;
        case 500:
          ElMessage.error('服务器内部错误');
          break;
        default:
          ElMessage.error(`登录失败 (${error.response.status})`);
      }
    } else if (error.request) {
      ElMessage.error('无法连接到服务器，请检查网络');
    } else {
      ElMessage.error('登录请求失败：' + error.message);
    }
  } finally {
    loading.value = false;
  }
};

const register = () => {
  router.push('/Register-component');
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  padding: 30px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.login-card:hover {
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

.login-form {
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

.login-button {
  width: 100%;
  height: 50px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(90deg, #0096ff 0%, #0081ff 100%);
  border: none;
  transition: all 0.3s;
}

.login-button:hover {
  opacity: 0.9;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 129, 255, 0.3);
}

.register-link {
  text-align: center;
  margin-top: 20px;
  color: #606266;
  font-size: 14px;
}

.register-text {
  color: #0081ff;
  cursor: pointer;
  font-weight: 500;
  transition: color 0.3s;
}

.register-text:hover {
  color: #409EFF;
  text-decoration: underline;
}

@media (max-width: 480px) {
  .login-card {
    padding: 20px;
  }
  
  .welcome-text {
    font-size: 20px;
    margin-bottom: 20px;
  }
  
  .custom-input :deep(.el-input__inner) {
    height: 45px;
  }
  
  .login-button {
    height: 45px;
  }
}
</style>
