import axios from 'axios';

const TOKEN_KEY = 'token';

export function getToken() {
  const token = localStorage.getItem(TOKEN_KEY);
  if (!token) {
    console.warn('Token不存在');
    return null;
  }
  return token;
}

export function setToken(token) {
  if (!token) {
    console.warn('尝试设置空token');
    return;
  }
  
  localStorage.setItem(TOKEN_KEY, token);
  // 设置axios默认请求头
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  console.log('Token已设置:', token);
}

export function removeToken() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem('userId');
  // 清除axios默认请求头
  delete axios.defaults.headers.common['Authorization'];
  console.log('Token已清除');
}

export function hasValidToken() {
  const token = getToken();
  return !!token;
} 