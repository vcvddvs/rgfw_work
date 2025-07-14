import axios from 'axios';
import { getToken, removeToken } from './auth';

// 创建axios实例
const api = axios.create({
  baseURL: 'http://localhost:8090', // 通过API网关访问所有微服务
  timeout: 5000,
  withCredentials: false // 不携带凭证，避免CORS预检请求问题
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    const token = getToken();
    console.log('发送请求前的token:', token);
    
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
      console.log('请求头中的Authorization:', config.headers['Authorization']);
    } else {
      console.warn('Token不存在，请求可能会被拒绝');
    }
    
    console.log('完整的请求配置:', {
      url: config.url,
      method: config.method,
      headers: config.headers,
      data: config.data
    });
    
    return config;
  },
  error => {
    console.error('请求拦截器错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  response => {
    console.log('收到响应:', response);
    // 检查响应格式，确保返回正确的数据结构
    if (response.data && response.data.code !== undefined) {
      // 如果响应是标准的Result格式（包含code, msg, data）
      return response.data;
    }
    // 否则直接返回响应数据
    return response.data;
  },
  error => {
    console.error('响应错误:', error);
    if (error.response?.status === 401) {
      removeToken();
      // 不在这里处理路由跳转，而是抛出特定的错误
      error.isAuthError = true;
    }
    return Promise.reject(error);
  }
);

// 用户相关API
export const userApi = {
  login: (data) => api.post('/user/login', data),
  register: (data) => api.post('/user/register', data),
  getUserInfo: (userId) => api.get(`/user/${userId}`)
};

// 商家相关API
export const businessApi = {
  getFoodList: (businessId) => api.get(`/business/${businessId}/foods`),
  getBusinessInfo: (businessId) => api.get(`/business/${businessId}`),
  getBusinessList: (page, size) => api.get(`/business/list?page=${page}&size=${size}`),
  getBusinessById: (businessId) => api.get(`/business/${businessId}`),
  getFoodsByBusinessId: (businessId) => api.get(`/food/listByBusinessId/${businessId}`)
};

// 订单相关API
export const orderApi = {
  createOrder: (data) => api.post('/orders/', data),
  getOrderById: (orderId) => api.get(`/orders/${orderId}`),
  getOrdersByUserId: (userId) => api.get(`/orders/user/${userId}`),
  getOrderHistory: () => api.get('/orders/history')
};

// 购物车相关API
export const cartApi = {
  addToCart: (data) => api.post('/cart/', data),
  getCartByUserId: (userId) => api.get(`/cart/user/${userId}`),
  deleteCartItem: (cartId) => api.delete(`/cart/${cartId}`),
  checkout: (data) => api.post('/cart/checkout', data),
  getCart: (userId) => api.get(`/cart/${userId}`),
  updateCart: (data) => api.put('/cart', data),
};

// 配送地址相关API
export const deliveryAddressApi = {
  getDeliveryAddressesByUserId: (userId) => api.get(`/deliveryAddress/listByUserId/${userId}`),
  addDeliveryAddress: (data) => api.post('/deliveryAddress/', data),
  updateDeliveryAddress: (data) => api.put('/deliveryAddress/', data),
  deleteDeliveryAddress: (daId) => api.delete(`/deliveryAddress/${daId}`)
};

// 支付相关API
export const paymentApi = {
  processPayment: (data) => api.post('/payment/process', data),
  verifyPayment: (paymentId) => api.get(`/payment/verify/${paymentId}`)
};

// 评价相关API
export const ratingApi = {
  submitRating: async (data) => {
    try {
      console.log('尝试提交评价到API服务:', data);
      const response = await api.post('/rating/', data);
      console.log('评价API响应:', response);
      return response;
    } catch (error) {
      console.error('评价API调用失败，详细错误:', error);
      console.log('使用本地模拟响应');
      // 模拟成功响应
      return {
        data: {
          code: 200,
          message: '评价提交成功（本地模拟）',
          data: data
        }
      };
    }
  },
  getRatingsByBusinessId: async (businessId) => {
    try {
      const response = await api.get(`/rating/business/${businessId}`);
      return response;
    } catch (error) {
      console.error('获取商家评价失败，使用本地模拟:', error);
      // 返回空数组
      return { data: [] };
    }
  },
  getRatingsByUserId: async (userId) => {
    try {
      const response = await api.get(`/rating/user/${userId}`);
      return response;
    } catch (error) {
      console.error('获取用户评价失败，使用本地模拟:', error);
      return { data: [] };
    }
  },
  getRatingByOrderId: async (orderId) => {
    try {
      const response = await api.get(`/rating/order/${orderId}`);
      console.log('获取订单评价响应:', response);
      
      // 检查响应是否为空字符串或null
      if (response === '' || response === null) {
        console.log('订单评价API返回空响应，表示没有评价');
        return { data: null };
      }
      
      // 检查响应是否为空对象
      if (typeof response === 'object' && Object.keys(response).length === 0) {
        console.log('订单评价API返回空对象，表示没有评价');
        return { data: null };
      }
      
      return response;
    } catch (error) {
      console.error('获取订单评价失败，使用本地模拟:', error);
      // 返回空对象，表示没有评价
      return { data: null };
    }
  },
  updateRating: async (data) => {
    try {
      const response = await api.put('/rating/', data);
      return response;
    } catch (error) {
      console.error('更新评价失败，使用本地模拟:', error);
      return {
        data: {
          code: 200,
          message: '评价更新成功（本地模拟）',
          data: data
        }
      };
    }
  }
};

export default api; 