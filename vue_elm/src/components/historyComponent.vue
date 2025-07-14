<template>
  <div class="wrapper">
    <div class="header">
      <div class="search">
        <div class="search-bottom">
          <div class="back">
            <span class="iconfont icon-back" @click="goBack"></span>
          </div>
          <div class="search-pt">
            <span class="iconfont icon-sousuo" style="margin-left: 2vw"></span>
            <input 
              style="flex:6;" 
              type="text" 
              placeholder="历史信息搜索" 
              v-model="searchQuery"
              @input="handleSearch"
            />
            <span 
              v-if="searchQuery" 
              class="clear-search" 
              @click="clearSearch"
            >
              ×
            </span>
          </div>
          <div class="box">
            <span class="iconfont icon-gongjuxiang"></span>
            <span class="iconfont icon-24gf-ellipsis"></span>
          </div>
        </div>
        <div class="information">
          <p :class="{ active: activeTab === 'all' }" @click="setActiveTab('all')">全部</p>
          <p :class="{ active: activeTab === 'inProgress' }" @click="setActiveTab('inProgress')">进行中</p>
          <p :class="{ active: activeTab === 'toRate' }" @click="setActiveTab('toRate')">待评价</p>
          <p :class="{ active: activeTab === 'rated' }" @click="setActiveTab('rated')">已评价</p>
        </div>
      </div>
    </div>
    <div class="body">
      <div v-if="filteredOrders.length === 0" class="no-results">
        <div class="no-results-icon">🔍</div>
        <div class="no-results-text">没有找到相关订单</div>
      </div>
      <div v-for="order in filteredOrders" :key="order.orderId" class="order-card">
        <!-- 订单头部：商家信息和订单总价 -->
        <div class="order-header">
          <div class="business-info">
            <img :src="getImageUrl(order.businessImg)" @error="handleImageError($event, 'business')" class="business-img"/>
            <div class="business-details">
              <div class="business-name">{{ order.businessName }}</div>
              <div class="delivery-price">{{ order.deliveryPrice }}元无门槛</div>
            </div>
          </div>
          <div class="order-price">&#165;{{ order.orderTotal }}</div>
        </div>
        
        <!-- 订单内容：食品列表 -->
        <div class="order-content">
          <div class="food-list">
            <div v-for="(foodName, index) in getArrayFromString(order.foodNames)" :key="index" class="food-item">
              <div class="food-left">
                <img 
                  :src="getFoodImage(order, index)" 
                  @error="handleImageError($event, 'food')" 
                  class="food-img"
                  alt="食品图片"
                  loading="lazy"
                />
              </div>
              <div class="food-right">
                <div class="food-name">{{ foodName || '未知食品' }}</div>
                <div class="food-quantity">x{{ getFoodQuantity(order, index) }}</div>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 订单底部：订单时间和操作按钮 -->
        <div class="order-footer">
          <div class="order-date">{{ order.orderDate }}</div>
          <div class="order-actions">
            <button class="reorder-btn" @click="reorder(order)">再来一单</button>
            <button 
              v-if="isOrderRated(order.orderId)" 
              class="rated-btn" 
              @click="goToRating(order)"
            >
              修改评价
            </button>
            <button 
              v-else 
              class="rating-btn" 
              @click="goToRating(order)"
            >
              评价订单
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { orderApi } from '../utils/api';
import { businessApi } from '../utils/api'; // Added import for businessApi
import { ElMessage } from 'element-plus'; // Added ElMessage import

const router = useRouter();
const orders = ref([]);
const activeTab = ref('all');
const baseURL = 'http://localhost:8090'; // API网关地址
const searchQuery = ref(''); // 搜索查询
const searchResults = ref([]); // 搜索结果

onMounted(() => {
  fetchOrders();
});

const goBack = () => {
  router.push('/Index-Component');
};

const setActiveTab = (tab) => {
  activeTab.value = tab;
  // 切换标签时滚动到顶部
  window.scrollTo(0, 0);
};

// 清除搜索
const clearSearch = () => {
  searchQuery.value = '';
  searchResults.value = [];
};

// 处理搜索
const handleSearch = () => {
  const query = searchQuery.value.trim().toLowerCase();
  
  if (!query) {
    searchResults.value = [];
    return;
  }
  
  // 在订单中搜索
  searchResults.value = orders.value.filter(order => {
    // 搜索商家名称
    if (order.businessName && order.businessName.toLowerCase().includes(query)) {
      return true;
    }
    
    // 搜索食品名称
    if (order.foodNames) {
      const foodNames = getArrayFromString(order.foodNames);
      if (foodNames.some(name => name.toLowerCase().includes(query))) {
        return true;
      }
    }
    
    // 搜索订单日期
    if (order.orderDate && order.orderDate.includes(query)) {
      return true;
    }
    
    // 搜索订单金额
    if (order.orderTotal && order.orderTotal.toString().includes(query)) {
      return true;
    }
    
    // 搜索订单状态
    const orderStateMap = {
      '待付款': 1,
      '待接单': 2,
      '配送中': 3,
      '已完成': 5,
      '待评价': 5,
      '已退款': 6
    };
    
    if (Object.keys(orderStateMap).some(key => 
      key.includes(query) && orderStateMap[key] === order.orderState
    )) {
      return true;
    }
    
    return false;
  });
};

// 监听搜索查询变化
watch(searchQuery, (newVal) => {
  if (!newVal.trim()) {
    searchResults.value = [];
  } else {
    handleSearch();
  }
});

// 将逗号分隔的字符串转换为数组
const getArrayFromString = (str) => {
  if (!str) return [];
  
  try {
    // 检查是否是Base64字符串集合
    if (typeof str === 'string' && str.includes('data:image')) {
      // 尝试找到所有的Base64图片
      const matches = str.match(/data:image\/[^;]+;base64,[A-Za-z0-9+/=]+/g);
      if (matches && matches.length > 0) {
        console.log(`找到 ${matches.length} 个完整的Base64图片`);
        return matches;
      }
    }
    
    // 检查是否包含多个iVBOR开头的Base64图片
    if (typeof str === 'string' && str.includes('iVBOR')) {
      // 尝试分割多个Base64字符串
      const parts = [];
      let currentIndex = 0;
      
      while (currentIndex < str.length) {
        const iVBORIndex = str.indexOf('iVBOR', currentIndex);
        if (iVBORIndex === -1) break;
        
        // 找到下一个iVBOR或字符串结束
        let nextIndex = str.indexOf('iVBOR', iVBORIndex + 5);
        if (nextIndex === -1) nextIndex = str.length;
        
        // 提取这段Base64数据
        const base64Part = str.substring(iVBORIndex, nextIndex).trim();
        if (base64Part.length > 100) {  // 确保是有效的Base64数据
          parts.push(base64Part);
        }
        
        currentIndex = nextIndex;
      }
      
      if (parts.length > 0) {
        console.log(`找到 ${parts.length} 个iVBOR开头的Base64图片`);
        return parts;
      }
    }
    
    // 如果不是特殊的Base64集合，则按照常规方式处理
    // 首先检查是否是单个Base64字符串
    if (typeof str === 'string' && str.length > 100 && 
        (str.startsWith('data:image') || str.startsWith('iVBOR') || 
         /^[A-Za-z0-9+/=]+$/.test(str.substring(0, 20)))) {
      return [str];
    }
    
    // 尝试按逗号分割，但需要注意不要分割Base64字符串中的逗号
    const items = [];
    const commaPositions = [];
    
    // 找出所有可能的逗号位置
    for (let i = 0; i < str.length; i++) {
      if (str[i] === ',') {
        commaPositions.push(i);
      }
    }
    
    // 检查每个逗号是否在Base64字符串内部
    let startPos = 0;
    for (const pos of commaPositions) {
      // 检查逗号前后的字符，判断是否是Base64内部的逗号
      const beforeComma = str.substring(Math.max(0, pos - 20), pos);
      const afterComma = str.substring(pos + 1, Math.min(str.length, pos + 21));
      
      // 如果逗号前后都是Base64字符，则可能是Base64内部的逗号
      const isBase64Comma = /^[A-Za-z0-9+/=]+$/.test(beforeComma) && 
                           /^[A-Za-z0-9+/=]+$/.test(afterComma);
      
      if (!isBase64Comma) {
        // 这是一个有效的分隔逗号
        const item = str.substring(startPos, pos).trim();
        if (item) items.push(item);
        startPos = pos + 1;
      }
    }
    
    // 添加最后一项
    const lastItem = str.substring(startPos).trim();
    if (lastItem) items.push(lastItem);
    
    // 如果没有成功分割，回退到简单的逗号分割
    if (items.length === 0) {
      return str.split(',').map(item => item.trim());
    }
    
    return items;
  } catch (error) {
    console.error('分割字符串出错:', error);
    // 出错时尝试简单的逗号分割
    try {
      return str.split(',').map(item => item.trim());
    } catch {
      return [str]; // 最后的回退方案
    }
  }
};

// 处理图片URL
const getImageUrl = (imgPath) => {
  if (!imgPath) {
    return require('@/assets/img/BUSINESS/img.png');
  }
  
  try {
    // 如果是Base64编码的图片数据
    if (typeof imgPath === 'string' && imgPath.length > 100) {
      return sanitizeBase64(imgPath);
    }
    
    // 如果是完整URL，直接返回
    if (imgPath.startsWith('http')) {
      return imgPath;
    }
    
    // 如果是以/assets开头的路径，转换为正确的路径
    if (imgPath.startsWith('/assets/')) {
      // 去掉开头的/assets/，因为在项目中assets是相对路径
      const relativePath = imgPath.replace('/assets/', '');
      try {
        return require(`@/assets/${relativePath}`);
      } catch (e) {
        // 尝试通过API网关获取
        return baseURL + imgPath;
      }
    }
    
    // 如果是相对路径，添加baseURL
    if (imgPath.startsWith('/')) {
      return baseURL + imgPath;
    }
    
    // 尝试加载项目中的图片
    try {
      // 首先尝试从BUSINESS目录加载
      if (!imgPath.includes('/')) {
        try {
          return require('@/assets/img/BUSINESS/' + imgPath);
        } catch (e) {
          // 尝试从其他目录加载
        }
      }
      
      // 然后尝试从普通img目录加载
      return require('@/assets/img/' + imgPath);
    } catch (e) {
      return require('@/assets/img/BUSINESS/img.png');
    }
  } catch (error) {
    console.error('处理图片URL出错:', error);
    return require('@/assets/img/BUSINESS/img.png');
  }
};

// 处理和验证Base64编码的图片
const sanitizeBase64 = (base64String) => {
  try {
    // 已经是完整的Base64字符串
    if (base64String.startsWith('data:image/')) {
      return base64String;
    }
    
    // 检查是否是PNG的Base64编码
    if (base64String.startsWith('iVBOR')) {
      return `data:image/png;base64,${base64String}`;
    }
    
    // 检查是否是JPEG的Base64编码
    if (base64String.startsWith('/9j/')) {
      return `data:image/jpeg;base64,${base64String}`;
    }
    
    // 检查是否包含无效字符
    if (!/^[A-Za-z0-9+/=]+$/.test(base64String.replace(/\s/g, ''))) {
      console.warn('Base64字符串包含无效字符');
      return '';
    }
    
    // 检查长度是否合理（Base64编码的图片通常很长）
    if (base64String.length < 100) {
      console.warn('Base64字符串长度不足');
      return '';
    }
    
    // 默认作为PNG处理
    return `data:image/png;base64,${base64String.replace(/\s/g, '')}`;
  } catch (error) {
    console.error('处理Base64字符串出错:', error);
    return '';
  }
};

// 处理图片加载错误
const handleImageError = (e, type) => {
  console.error(`图片加载失败: ${e.target.src.substring(0, 50)}...`);
  
  try {
    // 检查是否是Base64编码的图片加载失败
    const src = e.target.src;
    if (src && src.startsWith('data:image')) {
      console.log('尝试修复Base64图片...');
      
      // 提取Base64部分
      const base64Data = src.split(',')[1];
      if (base64Data) {
        // 尝试使用不同的MIME类型
        if (src.includes('image/png')) {
          e.target.src = `data:image/jpeg;base64,${base64Data}`;
          return;
        } else if (src.includes('image/jpeg')) {
          e.target.src = `data:image/png;base64,${base64Data}`;
          return;
        }
      }
    }
  } catch (error) {
    console.error('处理图片错误时出错:', error);
  }
  
  // 使用默认图片
  if (type === 'business') {
    e.target.src = require('@/assets/img/BUSINESS/img.png');
  } else {
    e.target.src = require('@/assets/img/sp01.png');
  }
};

// 再次下单
const reorder = (order) => {
  // 实现再次下单功能
  router.push(`/business-component/${order.businessId}`);
};

// 前往评价页面
const goToRating = (order) => {
  console.log('Order object:', order);
  
  // 确保businessId存在且为有效值
  const businessId = order.businessId || order.business?.businessId || '1';
  
  // 如果商家图片为空，尝试再次获取
  if (!order.businessImg && businessId) {
    businessApi.getBusinessById(businessId)
      .then(businessInfo => {
        if (businessInfo && businessInfo.businessImg) {
          navigateToRating(order, businessInfo.businessImg);
        } else {
          navigateToRating(order);
        }
      })
      .catch(error => {
        console.error('获取商家信息失败:', error);
        navigateToRating(order);
      });
  } else {
    navigateToRating(order);
  }
};

// 导航到评价页面
const navigateToRating = (order, businessImg = null) => {
  router.push({
    name: 'Rating',
    params: {
      orderId: order.orderId || '1',
      businessId: order.businessId || order.business?.businessId || '1',
      businessName: order.businessName || '未知商家'
    },
    query: {
      businessImg: businessImg || order.businessImg || '',
      orderDate: order.orderDate || ''
    }
  });
};

// 检查订单是否已评价
const isOrderRated = (orderId) => {
  try {
    // 检查本地存储中是否有该订单的评价
    const ratingsString = localStorage.getItem('localRatings');
    console.log(`检查订单 ${orderId} 是否已评价, 本地存储:`, ratingsString);
    
    if (ratingsString) {
      const ratings = JSON.parse(ratingsString);
      console.log('解析后的本地评价:', ratings);
      
      // 确保使用数字类型进行比较
      const orderIdNum = Number(orderId);
      const isRated = ratings.some(r => Number(r.orderId) === orderIdNum);
      console.log(`订单 ${orderId} 已评价状态:`, isRated);
      return isRated;
    }
  } catch (error) {
    console.error('检查订单评价状态失败:', error);
  }
  return false;
};

const fetchOrders = async () => {
  try {
    const response = await orderApi.getOrderHistory();
    console.log('订单历史原始响应:', response);
    
    if (response && Array.isArray(response)) {
      orders.value = response;
    } else if (response && response.data && Array.isArray(response.data)) {
      orders.value = response.data;
    } else if (response && typeof response === 'object') {
      // 尝试从响应中提取数据
      console.log('尝试从响应中提取订单数据:', response);
      const possibleOrderArrays = [
        response.data,
        response.result,
        response.content,
        response.records,
        response.list,
        response.orders
      ];
      
      for (const arr of possibleOrderArrays) {
        if (arr && Array.isArray(arr)) {
          console.log('找到订单数组:', arr);
          orders.value = arr;
          break;
        }
      }
    }
    
    // 详细记录每个订单的图片信息
    console.log('订单数据处理前:', JSON.stringify(orders.value.map(order => ({
      orderId: order.orderId,
      businessImg: order.businessImg ? order.businessImg.substring(0, 30) + '...' : 'null',
      businessImgType: order.businessImg ? typeof order.businessImg : 'null',
      hasBusinessImg: !!order.businessImg,
      foodImgs: order.foodImgs ? order.foodImgs.substring(0, 30) + '...' : 'null',
      foodImgsLength: order.foodImgs ? order.foodImgs.length : 0,
      foodNames: order.foodNames,
      foodNamesLength: order.foodNames ? order.foodNames.split(',').length : 0
    }))));
    
    // 确保每个订单对象都有必要的属性
    orders.value = orders.value.map(order => {
      // 使用数据库中存储的商家名称和图片
      const businessName = order.businessName || order.business?.businessName || '未知商家';
      const businessImg = order.businessImg || order.business?.businessImg || '';
      
      // 处理食品图片数据
      let foodImgs = order.foodImgs || '';
      const foodNames = order.foodNames || '';
      
      // 检查食品名称和图片数量是否匹配
      const foodNamesArray = foodNames.split(',').filter(name => name.trim());
      let foodImgsArray = getArrayFromString(foodImgs);
      
      console.log(`订单 ${order.orderId} 的食品名称数量: ${foodNamesArray.length}, 食品图片数量: ${foodImgsArray.length}`);
      
      // 如果食品图片数量少于食品名称数量，添加默认图片
      if (foodImgsArray.length < foodNamesArray.length) {
        console.log(`订单 ${order.orderId} 的食品图片数量不足，添加默认图片`);
        const defaultImg = '/assets/img/sp01.png';
        while (foodImgsArray.length < foodNamesArray.length) {
          foodImgsArray.push(defaultImg);
        }
        // 重新生成foodImgs字符串
        foodImgs = foodImgsArray.join(',');
      }
      
      // 如果有订单详情，尝试从中提取更多图片信息
      if (order.orderDetailList && Array.isArray(order.orderDetailList)) {
        const detailImages = order.orderDetailList.map(detail => detail.foodImg || '');
        console.log(`订单 ${order.orderId} 的详情图片:`, detailImages);
        
        // 如果详情中的图片比foodImgs更多，使用详情中的图片
        if (detailImages.filter(img => img).length > foodImgsArray.filter(img => img).length) {
          foodImgs = detailImages.join(',');
          console.log(`使用订单详情中的图片:`, foodImgs.substring(0, 50) + '...');
        }
      }
      
      return {
        ...order,
        businessId: order.businessId || order.business?.businessId || '1',
        businessName: businessName,
        businessImg: businessImg,
        foodImgs: foodImgs, // 更新后的食品图片
        orderDate: order.orderDate || new Date(order.orderTime || Date.now()).toLocaleString()
      };
    });
    
    console.log('处理后的订单数据:', orders.value.map(order => ({
      orderId: order.orderId,
      foodImgsLength: order.foodImgs ? order.foodImgs.length : 0,
      foodNamesLength: order.foodNames ? order.foodNames.split(',').length : 0
    })));
    
  } catch (error) {
    console.error('获取订单历史失败:', error);
    
    // 如果API调用失败，尝试从本地存储获取订单信息
    const localOrderInfo = localStorage.getItem('orderInfo');
    if (localOrderInfo) {
      try {
        const orderInfo = JSON.parse(localOrderInfo);
        console.log('使用本地存储的订单信息:', orderInfo);
        
        // 创建模拟订单数据
        const mockOrder = {
          orderId: new Date().getTime(),
          businessId: orderInfo.businessId,
          businessName: orderInfo.businessName,
          businessImg: orderInfo.businessImg,
          orderDate: orderInfo.orderDate || new Date().toLocaleString(),
          orderTotal: orderInfo.orderTotal || 0,
          orderState: 5, // 已完成/待评价
          foodNames: orderInfo.items?.map(item => item.foodName).join(','),
          foodImgs: orderInfo.items?.map(item => item.foodImg || '/assets/img/sp01.png').join(','),
          quantities: orderInfo.items?.map(item => item.quantity).join(',')
        };
        
        // 使用模拟数据
        orders.value = [mockOrder];
        
        // 显示提示
        ElMessage({
          message: 'API服务不可用，显示本地存储的订单信息',
          type: 'warning',
          duration: 3000
        });
      } catch (e) {
        console.error('解析本地订单信息失败:', e);
        
        // 使用硬编码的模拟数据
        useMockData();
      }
    } else {
      // 如果本地存储中没有订单信息，使用硬编码的模拟数据
      useMockData();
    }
  }
};

// 使用硬编码的模拟数据
const useMockData = () => {
  console.log('使用硬编码的模拟订单数据');
  
  // 创建模拟订单数据
  const mockOrders = [
    {
      orderId: 1,
      businessId: 1,
      businessName: '万家饺子（软件园E18店）',
      businessImg: '/assets/img/BUSINESS/img.png',
      orderDate: new Date().toLocaleString(),
      orderTotal: 31,
      orderState: 5,
      foodNames: '纯肉鲜肉,玉米鲜肉',
      foodImgs: '/assets/img/sp01.png,/assets/img/sp01.png',
      quantities: '1,1'
    },
    {
      orderId: 2,
      businessId: 2,
      businessName: '肯德基麦当劳',
      businessImg: '/assets/img/BUSINESS/img.png',
      orderDate: new Date(Date.now() - 86400000).toLocaleString(), // 昨天
      orderTotal: 45,
      orderState: 5,
      foodNames: '汉堡,薯条,可乐',
      foodImgs: '/assets/img/sp01.png,/assets/img/sp01.png,/assets/img/sp01.png',
      quantities: '1,1,1'
    }
  ];
  
  // 使用模拟数据
  orders.value = mockOrders;
  
  // 显示提示
  ElMessage({
    message: 'API服务不可用，显示模拟订单数据',
    type: 'warning',
    duration: 3000
  });
};

// 根据选中的标签和搜索结果筛选订单
const filteredOrders = computed(() => {
  // 如果有搜索结果，优先显示搜索结果
  if (searchQuery.value.trim() && searchResults.value.length > 0) {
    // 进一步根据标签筛选搜索结果
    if (activeTab.value === 'all') {
      return searchResults.value;
    } else if (activeTab.value === 'inProgress') {
      // 订单状态：1-已下单/待付款，2-已付款/待接单，3-已接单/配送中
      return searchResults.value.filter(order => [1, 2, 3].includes(order.orderState));
    } else if (activeTab.value === 'toRate') {
      // 未评价的订单 - 显示所有已完成但未评价的订单
      return searchResults.value.filter(order => {
        const rated = isOrderRated(order.orderId);
        console.log(`搜索结果中订单 ${order.orderId} 评价状态:`, rated);
        return !rated; // 未评价的订单
      });
    } else if (activeTab.value === 'rated') {
      // 已评价的订单
      return searchResults.value.filter(order => {
        const rated = isOrderRated(order.orderId);
        console.log(`搜索结果中订单 ${order.orderId} 评价状态:`, rated);
        return rated; // 已评价的订单
      });
    }
    return searchResults.value;
  }
  
  // 如果没有搜索或搜索结果为空，使用原始筛选逻辑
  if (activeTab.value === 'all') {
    return orders.value;
  } else if (activeTab.value === 'inProgress') {
    // 订单状态：1-已下单/待付款，2-已付款/待接单，3-已接单/配送中
    return orders.value.filter(order => [1, 2, 3].includes(order.orderState));
  } else if (activeTab.value === 'toRate') {
    // 未评价的订单 - 显示所有未评价的订单，不考虑订单状态
    const unratedOrders = orders.value.filter(order => {
      const rated = isOrderRated(order.orderId);
      console.log(`订单 ${order.orderId} 评价状态:`, rated);
      return !rated; // 未评价的订单
    });
    console.log('未评价订单数量:', unratedOrders.length);
    return unratedOrders;
  } else if (activeTab.value === 'rated') {
    // 已评价的订单
    const ratedOrders = orders.value.filter(order => {
      const rated = isOrderRated(order.orderId);
      console.log(`订单 ${order.orderId} 评价状态:`, rated);
      return rated; // 已评价的订单
    });
    console.log('已评价订单数量:', ratedOrders.length);
    return ratedOrders;
  }
  return orders.value;
});

// 获取食品图片的辅助函数
const getFoodImage = (order, index) => {
  try {
    // 获取食品图片数组
    const foodImgs = getArrayFromString(order.foodImgs);
    console.log(`获取订单 ${order.orderId} 的第 ${index} 个食品图片, 图片数组长度: ${foodImgs.length}`);
    
    // 检查索引是否有效
    if (index >= 0 && index < foodImgs.length) {
      const imgPath = foodImgs[index];
      console.log(`食品图片路径: ${imgPath ? (imgPath.length > 50 ? imgPath.substring(0, 50) + '...' : imgPath) : 'null'}`);
      
      // 检查是否是Base64编码
      if (imgPath && imgPath.length > 100) {
        return sanitizeBase64(imgPath);
      }
      
      // 检查是否是空字符串或无效路径
      if (!imgPath || imgPath === 'null' || imgPath === 'undefined') {
        console.log('图片路径无效，返回默认图片');
        return require('@/assets/img/sp01.png');
      }
      
      // 处理普通图片URL
      console.log('使用getImageUrl处理普通图片URL');
      return getImageUrl(imgPath);
    }
    
    // 如果索引无效，返回默认图片
    console.log('索引无效，返回默认图片');
    return require('@/assets/img/sp01.png');
  } catch (error) {
    console.error('获取食品图片出错:', error, error.stack);
    return require('@/assets/img/sp01.png');
  }
};

// 获取食品数量的辅助函数
const getFoodQuantity = (order, index) => {
  try {
    // 获取数量数组
    const quantities = getArrayFromString(order.quantities);
    
    // 检查索引是否有效
    if (index >= 0 && index < quantities.length) {
      return quantities[index] || '1';
    }
    
    // 如果索引无效，返回默认值
    return '1';
  } catch (error) {
    console.error('获取食品数量出错:', error);
    return '1';
  }
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
  background-color: #f5f5f5;
  padding-bottom: 20px;
}

.header {
  background-color: white;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  z-index: 10;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.search-bottom {
  display: flex;
  align-items: center;
  padding: 10px;
  height: 40px;
}

.back .icon-back {
  font-size: 20px;
  padding: 0 10px;
}

.search-pt {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
}

.search-pt input {
  width: 100%;
  border: none;
  background-color: #f5f5f5;
  padding: 8px 8px 8px 30px;
  border-radius: 20px;
  font-size: 14px;
}

.search-pt .icon-sousuo {
  position: absolute;
  left: 10px;
  font-size: 16px;
  color: #999;
}

.box span {
  padding: 0 10px;
  font-size: 20px;
}

.information {
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.information p {
  padding: 5px 10px;
  cursor: pointer;
  position: relative;
}

.information p.active {
  color: #0097ff;
  font-weight: bold;
}

.information p.active::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 50%;
  transform: translateX(-50%);
  width: 20px;
  height: 3px;
  background-color: #0097ff;
  border-radius: 3px;
}

.body {
  margin-top: 100px;
  padding: 10px;
}

.order-card {
  background-color: white;
  border-radius: 10px;
  margin-bottom: 15px;
  padding: 15px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.business-info {
  display: flex;
  align-items: center;
}

.business-img {
  width: 50px;
  height: 50px;
  border-radius: 5px;
  object-fit: cover;
  margin-right: 10px;
}

.business-details {
  display: flex;
  flex-direction: column;
}

.business-name {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 5px;
}

.delivery-price {
  font-size: 12px;
  color: #ff6b01;
  background-color: #fff5e6;
  padding: 2px 5px;
  border-radius: 3px;
  display: inline-block;
}

.order-price {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.order-content {
  padding: 10px 0;
}

.food-list {
  display: flex;
  flex-direction: column;
}

.food-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 10px;
}

.food-left {
  margin-right: 15px;
}

.food-img {
  width: 60px;
  height: 60px;
  border-radius: 5px;
  object-fit: cover;
}

.food-img-placeholder {
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f0f0;
  border-radius: 5px;
}

.food-img-placeholder img {
  width: 30px;
  height: 30px;
}

.food-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.food-name {
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 8px;
}

.food-quantity {
  font-size: 12px;
  color: #666;
  background-color: #f0f0f0;
  padding: 2px 8px;
  border-radius: 12px;
  display: inline-block;
  width: fit-content;
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #f0f0f0;
}

.order-date {
  font-size: 12px;
  color: #999;
}

.order-actions {
  display: flex;
  gap: 10px;
}

.reorder-btn, .rating-btn, .rated-btn {
  border: none;
  padding: 8px 15px;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
}

.reorder-btn {
  background-color: #0097ff;
  color: white;
}

.rating-btn {
  background-color: #ff6b01;
  color: white;
}

.rated-btn {
  background-color: #4caf50;
  color: white;
}

.clear-search {
  position: absolute;
  right: 10px;
  font-size: 18px;
  color: #999;
  cursor: pointer;
  padding: 5px;
}

.no-results {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 0;
  color: #999;
}

.no-results-icon {
  font-size: 50px;
  margin-bottom: 10px;
}

.no-results-text {
  font-size: 16px;
}
</style>