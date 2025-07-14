<template>

  <div class="wrapper">
    <div class="header">
      <div class="location">
        <div class="icon">
          <div class="icon-text">常点 | 推荐</div>
        </div>
        <div class="location-text">
          云南大学呈贡校区<i class="fa-solid fa-caret-down"></i>
        </div>
      </div>
      <div class="search">
        <div class="search-panel">
          <i style="flex: 0 0 6vw; padding-left: 3vw; font-size: 4vw;color: #17BAF9;" class="fa-solid fa-expand"></i>
          <input 
            style="flex:6;" 
            type="text" 
            placeholder="请输入要查询的商品" 
            v-model="searchQuery"
            @keyup.enter="searchClick"
          />
          <div style="flex:0 0 17vw" class="search-button" @click="searchClick">搜索</div>
        </div>
      </div>
    </div>
    <div class="foodType">
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class01.jpg')" >
        <p>美食外卖</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class02.jpg')" alt="描述" />
        <p>超市便利</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class03.jpg')" alt="描述" />
        <p>水果鲜花</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class04.jpg')" alt="描述" />
        <p>买菜</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class05.jpg')" alt="描述" />
        <p>买药</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class06.jpg')" alt="描述" />
        <p>甜品饮品</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class07.jpg')" alt="描述" />
        <p>天天爆红包</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class08.jpg')" alt="描述" />
        <p>0元领水果</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class09.jpg')" alt="描述" />
        <p>跑腿</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class10.jpg')" alt="描述" />
        <p>天天特价</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class11.jpg')" alt="描述" />
        <p>借钱</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class12.jpg')" alt="描述" />
        <p>粥粉面点</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class13.jpg')" alt="描述" />
        <p>宠物用品</p>
      </div>
      <div class="foodTypeItem" onclick="location.href='businessList2.html'">
        <img :src="require('@/assets/img/TypeImg/class14.jpg')" alt="描述" />
        <p>奶茶果汁</p>
      </div>
      <div class="foodTypeItem">
        <img :src="require('@/assets/img/TypeImg/class15.jpg')" alt="描述" />
        <p>全部</p>
      </div>
    </div>
    <!-- <button class="banner-carousel-btn prev">Prev</button>
    <button class="banner-carousel-btn next">Next</button> -->
    <!--广告部分-->
    <div class="banner-carousel">
      <div
          v-for="(ad, index) in ads"
          :key="ad.key"
          class="banner"
          :class="{ active: index === currentAdIndex }"
      >
        <div class="banner-left">
          <div class="banner-left-top">
            <img :src="ad.leftTopImg" alt="描述" height="27" width="27"/>
            <p class="e-little-baby">饿小宝</p>
            <p style="font-weight: bold;font-size:3.3vw;margin-top: 1vw;margin-left: 20px">{{ ad.leftText }}</p>
          </div>
          <div class="banner-left-content">
            <div class="banner-left-content-left">
              <p class="banner-left-text">{{ ad.leftContentLeftText }}</p>
              <div class="banner-left-content-left-button">去看看</div>
            </div>
            <div class="banner-left-content-right">
              <img :src="ad.leftContentRightImg" alt="描述" />
            </div>
          </div>
        </div>
        <div class="banner_right">
          <div v-for="item in ad.rightItems" :key="item.title" class="banner_right_item">
            <p class="title">{{ item.title }}</p>
            <img :src="item.img" alt="描述" />
            <p class="remark">{{ item.remark }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="recommend">
      <div>
        <p style="color: #17BAF9;font-weight: bolder">换换口味</p>
      </div>
      <div>
        <p>天天爆红包</p>
      </div>
      <div>
        <p>减配送费</p>
      </div>
      <div>
        <p>学生特价</p>
      </div>
      <div>
        <i class="fa-solid fa-bars"></i>
      </div>
    </div>
    
    <!-- 搜索结果提示 -->
    <div v-if="isSearching" class="search-status">
      <div class="search-status-text">
        {{ searchResults.length > 0 ? `搜索"${searchQuery}"的结果：${searchResults.length}个商家` : `没有找到与"${searchQuery}"相关的商家` }}
      </div>
      <div class="search-status-clear" @click="clearSearch">清除搜索</div>
    </div>
    
    <div class="foodList" @scroll="handleScroll">
      <div v-if="displayBusinesses.length === 0" class="no-results">
        <div class="no-results-icon">🔍</div>
        <div class="no-results-text">没有找到相关商家</div>
      </div>
      <div class="food" v-for="business in displayBusinesses" :key="business.businessId" @click="goToBusinessList(business)">
        <img :src="processImageUrl(business.businessImg)" 
             alt="商家图片" 
             height="120" 
             width="110"
             @error="handleImageError" />
        <div class="business-into">
          <div class="business-h">
            <h>{{ business.businessName }}</h>
          </div>
          <div class="star">
            <p style="color: #ff4a25;font-weight: bolder;font-size: 14px">{{ business.starPrice }}分</p>
            <p2>月售{{ business.orderTypeId }}+</p2>
            <p3>{{ business.deliveryPrice }}分钟 {{ business.distance }}km</p3>
          </div>
          <div class="for-sell">
            <p1>起送费: {{ business.starPrice }}元</p1>
            <p2>配送费: {{ business.deliveryPrice }}元</p2>
          </div>
          <!-- 根据实际业务需求添加更多信息 -->
        </div>
      </div>
      <!-- 加载更多提示 -->
      <div v-if="!hasMore && !isSearching" class="load-more">
        已加载全部商家
      </div>
    </div>

    <div>&nbsp;</div>
    <div class="footer">
      <div>
        <i class="fa-solid fa-bowl-rice"></i>
        <p>首页</p>
      </div>
      <div>
        <i class="fa-solid fa-store"></i>
        <p>全能超市</p>
      </div>
      <div @click="goToHistory">
        <i class="fa-solid fa-comment-dots"></i>
        <p>历史消息</p>
      </div>
      <div>
        <i class="fa-solid fa-cart-plus"></i>
        <p>购物车</p>
      </div>
      <div @click="goToLogin">
        <i class="fa-solid fa-user"></i>
        <p>登录</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { businessApi } from '@/utils/api';
import axios from 'axios';

const businesses = ref([]);
const page = ref(1);
const pageSize = ref(10);
const hasMore = ref(true);
const router = useRouter();
import { ref, onMounted, onUnmounted, computed } from 'vue';

// 搜索相关状态
const searchQuery = ref('');
const searchResults = ref([]);
const isSearching = ref(false);

// 计算属性：显示的商家列表
const displayBusinesses = computed(() => {
  return isSearching.value ? searchResults.value : businesses.value;
});

// 搜索功能
const searchClick = () => {
  const query = searchQuery.value.trim();
  
  if (!query) {
    clearSearch();
    return;
  }
  
  isSearching.value = true;
  
  // 在商家列表中搜索
  searchResults.value = businesses.value.filter(business => {
    // 搜索商家名称
    if (business.businessName && business.businessName.toLowerCase().includes(query.toLowerCase())) {
      return true;
    }
    
    // 搜索商家类型
    if (business.businessExplain && business.businessExplain.toLowerCase().includes(query.toLowerCase())) {
      return true;
    }
    
    // 搜索配送范围
    if (business.deliveryPrice && business.deliveryPrice.toString().includes(query)) {
      return true;
    }
    
    // 搜索起送价
    if (business.starPrice && business.starPrice.toString().includes(query)) {
      return true;
    }
    
    return false;
  });
  
  console.log(`搜索"${query}"的结果:`, searchResults.value.length);
};

// 清除搜索
const clearSearch = () => {
  searchQuery.value = '';
  searchResults.value = [];
  isSearching.value = false;
};

// 定义广告数据
const ads = ref([
  {
    key: 'ad1',
    leftTopImg: require('@/assets/img/TypeImg/img.png'),
    leftText: '在线订单',
    leftContentLeftText: '一起来嗨',
    leftContentRightImg: require('@/assets/img/TypeImg/bannerLeft-Right.png'),
    rightItems: [
      { title: '晚餐聚会', img: require('@/assets/img/BannerImg/banner01.jpg'), remark: '一起吃好点' },
      { title: '学生福利', img: require('@/assets/img/BannerImg/banner02.jpg'), remark: '最高省912' },
    ],
  },
  {
    key: 'ad2',
    leftTopImg: require('@/assets/img/BannerImg/banner04.jpg'),
    leftText: '深夜食堂',
    leftContentLeftText: '都是好吃的',
    leftContentRightImg: require('@/assets/img/BannerImg/banner03.jpg'),
    rightItems: [
      { title: '深夜解馋', img: require('@/assets/img/BannerImg/img_5.jpg'), remark: '领夜宵红包' },
      { title: '学生福利', img: require('@/assets/img/BannerImg/img_6.jpg'), remark: '最高省912' },
    ],
  },
]);

const currentAdIndex = ref(0); // 当前激活的广告索引

const switchAd = () => {
  currentAdIndex.value = (currentAdIndex.value + 1) % ads.value.length;
};

onMounted(() => {
  const intervalId = setInterval(switchAd, 3000); // 每3秒切换一次广告
  onUnmounted(() => {
    clearInterval(intervalId);
  });
});
const fetchBusinesses = async () => {
  try {
    console.log('开始获取商家列表，页码:', page.value, '每页数量:', pageSize.value);
    
    // 直接使用axios发送请求，查看原始响应
    const axiosResponse = await axios.get(`http://localhost:8090/business/list?page=${page.value}&size=${pageSize.value}`);
    console.log('Axios商家列表原始响应:', axiosResponse);
    
    const response = await businessApi.getBusinessList(page.value, pageSize.value);
    console.log('商家列表响应:', response);
    
    // Check if response has the Result format (code, msg, data structure)
    if (response && response.code === 1 && Array.isArray(response.data)) {
      console.log('响应格式: Result<List<Business>>');
      if (response.data.length < pageSize.value) {
        hasMore.value = false; // 如果返回的数据少于pageSize，表示没有更多数据了
      }
      
      // 添加调试日志，检查每个商家对象
      response.data.forEach((business, index) => {
        console.log(`商家 ${index + 1}:`, business);
        console.log(`- ID: ${business.businessId}`);
        console.log(`- 名称: ${business.businessName}`);
        console.log(`- 图片: ${business.businessImg ? '有图片' : '无图片'}`);
      });
      
      businesses.value.push(...response.data);
    } else if (Array.isArray(response)) {
      // Fallback for direct array response
      console.log('响应格式: List<Business>');
      if (response.length < pageSize.value) {
        hasMore.value = false;
      }
      
      // 添加调试日志，检查每个商家对象
      response.forEach((business, index) => {
        console.log(`商家 ${index + 1}:`, business);
      });
      
      businesses.value.push(...response);
    } else if (response && typeof response === 'object') {
      // 尝试直接从响应中获取数据
      console.log('尝试从响应中提取数据:', response);
      const data = response.data || response.result || response;
      if (Array.isArray(data)) {
        console.log('从响应中提取到数组数据');
        if (data.length < pageSize.value) {
          hasMore.value = false;
        }
        
        // 添加调试日志，检查每个商家对象
        data.forEach((business, index) => {
          console.log(`商家 ${index + 1}:`, business);
        });
        
        businesses.value.push(...data);
      } else {
        console.error('获取商家列表失败: 无法从响应中提取数组数据', response);
      }
    } else {
      console.error('获取商家列表失败: 返回数据格式不正确', response);
    }
  } catch (error) {
    console.error('获取商家列表失败:', error);
  }
};

const handleScroll = () => {
  const scrollElement = document.querySelector('.foodList');
  if (scrollElement.scrollHeight - scrollElement.scrollTop === scrollElement.clientHeight && hasMore.value) {
    page.value += 1; // 增加页码
    fetchBusinesses(); // 调用获取商家列表的方法
  }
};


onMounted(() => {
  fetchBusinesses(); // 组件挂载后立即获取商家列表
});


const goToHistory = () => {
  router.push('/history-component');
};

const goToLogin = () => {
  router.push('/login-component');
};

const goToBusinessList = (business) => {
  router.push({
    name: 'BusinessList2',
    params: {businessId: business.businessId, businessName: business.businessName, businessImg: business.businessImg}
  });
};

const handleImageError = (event) => {
  event.target.src = require('@/assets/img/TypeImg/img.png');
};

const processImageUrl = (imgUrl) => {
  // 如果URL为空，返回默认图片
  if (!imgUrl) {
    console.log('图片URL为空，使用默认图片');
    return require('@/assets/img/TypeImg/img.png');
  }
  
  // 如果URL是字符串类型
  if (typeof imgUrl === 'string') {
    // 如果已经是完整的URL或data:image格式，直接返回
    if (imgUrl.startsWith('http') || imgUrl.startsWith('data:')) {
      console.log('使用原始图片URL(Base64或HTTP)');
      return imgUrl;
    }
    
    // 尝试作为相对路径处理
    try {
      console.log('尝试作为相对路径处理:', imgUrl);
      return require('@/assets/img/TypeImg/img.png');
    } catch (e) {
      console.log('图片路径处理失败，使用默认图片');
      return require('@/assets/img/TypeImg/img.png');
    }
  }
  
  // 如果URL是对象或其他类型，返回默认图片
  console.log('图片URL类型不是字符串，使用默认图片');
  return require('@/assets/img/TypeImg/img.png');
};
</script>


<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
}

/*顶部样式*/
/*logo样式*/
.wrapper .header {
  width: 100%;
  display: flex;
  flex-direction: column;
  color: white;
  align-items: center;
  position: fixed; /* 固定在页面上 */
  top: 0; /* 距离顶部的距离 */
  left: 0; /* 距离左侧的距离 */
  right: 0; /* 距离右侧的距离，与left一起使用时，可以水平居中 */
  background-color: white;


  justify-content: space-between; /* 水平对齐，输入框在左，按钮在右 */
  border-bottom: 1px solid #ddd; /* 添加底部边框以增强视觉分隔 */

}

.wrapper .header .location {
  width: 100%;
  height: 16vw;
  display: flex;
  flex-direction: row;
  align-items: center;
  background-image: url('@/assets/img_4.png');
  background-size: cover;
  background-position: top;
}

.wrapper .header .location .icon {
  display: flex;
  font-size: 5vw;
  padding-left: 2vw;
  flex: 4;
  margin: 1vw;

}

.wrapper .header .location .icon .icon-text {
  margin: 0 3vw 0 3vw;

}

.wrapper .header .location .location-text {
  display: flex;
  justify-content: flex-end;
  padding-right: 2vw;
  flex: 6;
  font-size: 4.5vw;
}

.wrapper .header .location .location-text .fa-caret-down {
  margin-left: 2vw;
}

/*search样式*/
.wrapper .header .search {
  width: 100%;
  height: 12vw;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

}

.wrapper .header .search .search-panel {
  width: 95%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border: 2px solid #17BAF9;
  height: 8vw;

  border-radius: 25px;
  color: #9F9F9F;
}

.wrapper .header .search .search-panel input {
  outline: none;
  border: none;
  color: black;
  font-size: 4vw;
  background-color: white;
}

.wrapper .header .search .search-panel .search-button {
  cursor: pointer;
  height: 7vw;
  margin-right: 2px;
  border-radius: 30px;
  background-color: #17BAF9;
  color: white;
  font-size: 4vw;
  display: flex;
  align-items: center;
  justify-content: center;
}


/*foodType样式*/
.wrapper .foodType {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-content: center;
  height: 45vw;
  margin-top: 28vw;
}

.wrapper .foodType .foodTypeItem {
  display: flex;
  flex-direction: column;
  height: 15vw;
  width: 18vw;
  cursor: pointer;
  align-items: center;
}

.wrapper .foodType .foodTypeItem img {
  width: 12vw;
  height: 8vw;
}

.wrapper .foodType .foodTypeItem p {
  font-size: 3.2vw;
  color: #666666;
  margin-top: 0.1vw;
}

/*广告样式*/
.banner {
  display: none;
  position: relative;
  width: 100%;
  z-index: 1;
}

.banner.active {
  display: block;
  animation: fadeAnimation 1s ease-in-out;
}

@keyframes fadeAnimation {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.wrapper .banner {
  width: 100%;
  display: flex;
}

.wrapper .banner .banner-left {
  display: flex;
  flex: 5;
  flex-direction: column;
  height: 25vw;
  border: aliceblue 2px solid;
  border-radius: 5px;
}

.wrapper .banner .banner-left-top {
  margin-top: 2vw;
  display: flex;
  height: 5vw;
  flex: 1;
}

.wrapper .banner .banner-left-top .e-little-baby {
  font-family: 微软雅黑;
  font-style: italic;
  font-weight: bolder;
  font-size: 3.4vw;
  margin-top: 1vw;
}

.wrapper .banner .banner-left-top img {
  width: 5vw;
  height: 5vw;
  margin-left: 2vw;
}

.wrapper .banner .banner-left-content {
  display: flex;
  flex: 3;
}

.wrapper .banner .banner-left-content .banner-left-text {
  font-family: 微软雅黑;
  font-weight: bolder;
  font-size: 3.3vw;
  margin-top: 4vw;
  margin-left: 6vw;

  display: flex;
}

.wrapper .banner .banner-left-content .banner-left-content-left-button {
  cursor: pointer;
  height: 8vw;
  width: 15vw;
  border-radius: 25px;
  background-color: aliceblue;
  color: #17BAF9;
  font-weight: bolder;
  font-size: 3vw;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 4vw;
  margin-top: -2vw;
}

.wrapper .banner .banner-left-content-left {
  flex: 1.5;
  display: flex;
  flex-direction: column;
}

.wrapper .banner .banner-left-content-right {
  flex: 1;
}

.wrapper .banner .banner-left-content-right img {
  width: 27vw;
  height: 16.8vw;
}

.wrapper .banner .banner_right {
  flex: 5;
  display: flex;
  justify-content: center;
  align-items: center;


}

.wrapper .banner .banner_right .banner_right_item {
  flex: 5;
  height: 25vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: aliceblue 2px solid;
  border-radius: 5px;
  margin: 0 0.5vw 0 0.5vw;

}

.wrapper .banner .banner_right .banner_right_item .title {

  font-size: 3.3vw;
  font-weight: bolder;
  margin-bottom: 0.2vw;
  margin-top: 2vw;
}

.wrapper .banner .banner_right .banner_right_item img {
  width: 10vw;
  height: 10vw;
  margin-top: 0.5vw;
}

.wrapper .banner .banner_right .banner_right_item .remark {
  margin-top: 1.5vw;
  font-size: 2.3vw;
  margin-bottom: 0.5vw;

  color: #999999;
}

/*目录样式*/
.wrapper .recommend {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}


.wrapper .recommend div p {
  color: #666666;
  font-size: 3.2vw;


}

/*食物列表*/

.wrapper .foodList .food {
  display: flex;
  padding: 2.5vw;
  user-select: none;

}


.wrapper .foodList .food .business-into {
  width: 65%;
  box-sizing: border-box;
  padding-left: 3vw;
}

.wrapper .foodList .food .business-into .business-h {
  display: flex;
  align-items: center;
  margin-bottom: 2vw;
}

.wrapper .foodList .food .business-into .business-h h {
  color: black;
  font-size: 4.5vw;
  font-weight: bolder;
}

.wrapper .foodList .food .business-into .star {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5vw;

}

.wrapper .foodList .food .business-into .star p {
  margin: 0 0 0;
}


.wrapper .foodList .food .business-into .star p2 {
  display: flex;
  color: #ff8b39;
  padding-right: 3vw;
  font-size: 12px;

}

.wrapper .foodList .food .business-into .star p3 {
  color: #999999;
  font-size: 12px;
}

.wrapper .foodList .food .business-into .for-sell {
  display: flex;
  align-items: center;
  margin-bottom: 5vw;
}

.wrapper .foodList .food .business-into .for-sell p1 {
  border: 1px solid #ff8b39;
  color: #ff8b39;
  padding-left: 1vw;
  padding-right: 1vw;
  border-radius: 5px;
  font-size: 11px;
}

.wrapper .foodList .food .business-into .for-sell p2 {
  margin-left: 3vw;
  border: 1px solid #ffd109;
  color: #ffd109;
  padding-left: 1vw;
  padding-right: 1vw;
  border-radius: 5px;
  font-size: 11px;
}


.wrapper .foodList .food .business-into .menus div {
  margin-left: 0.5vw;
}

.wrapper .foodList .food .business-into .menus p {
  margin-top: 1vw;
  margin-bottom: 1vw;
  font-weight: bolder;
}

.wrapper .foodList .food .business-into .menus .slide .price {
  font-size: 4vw;
  color: #ff4a25;
}

.wrapper .foodList .food .business-into .menus .slide img {
  width: 25vw;
  height: 25vw;
}

.wrapper .foodList .food .business-into .comment {
  display: flex;
  font-size: 3vw;
  align-items: center;
  height: 4.5vw;
}


.wrapper .foodList .food .business-into .comment p {
  border: none;
  color: coral;
  padding: 1.2vw 1vw 1.2vw 1vw;
  background-color: #ffe99a;
  border-radius: 5px;
  margin-right: 2vw;
}

.wrapper .foodList .food .business-into .ticket {
  margin-top: 3.2vw;
  display: flex;
  align-items: center;
  font-size: 3vw;


}

.wrapper .foodList .food .business-into .ticket .img {
  display: flex;
  align-items: center;
  margin: 0 1vw 0 1vw;
  border: 1px solid saddlebrown;
  color: saddlebrown;
  border-radius: 5px;
}

.wrapper .foodList .food .business-into .ticket .img p {
  margin: 0 2vw 0 2vw;

}


.wrapper .foodList .food .business-into .ticket .ps {
  display: flex;
  align-items: center;
}

.wrapper .foodList .food .business-into .ticket .ps p {
  margin: 0 1vw 0 1vw;
  padding: 0 1vw 0 1vw;
  border: 1px solid saddlebrown;
  color: saddlebrown;
  border-radius: 5px;
}


/*底部样式*/
.wrapper .footer {
  width: 100%;
  height: 14vw;
  border-top: solid 1px #DDD;
  background-color: white;
  position: fixed;
  left: 0;
  bottom: 0;

  display: flex;
  justify-content: space-around;
  align-items: center;
}

.wrapper .footer div {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #999999;
  cursor: pointer;
  user-select: none;
  padding-top: 4vw;
}

.wrapper .footer div p {
  font-size: 2.8vw;
}

.wrapper .footer div i {
  font-size: 6vw;
}

.banner-carousel {
  position: relative;
  width: 100%;
  /* Adjust to your needs */
  height: 125px;
  /* Adjust to your needs */

}

.banner {
  opacity: 0;
  transition: opacity 1s ease-in-out;
  position: absolute;
}

.banner.active {
  opacity: 1;
}

.banner-carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  padding: 10px 20px;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  border: none;
  cursor: pointer;
  z-index: 10;
}

/* 新增样式 */
.search-status {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1vw 2vw;
  background-color: #ffe99a;
  border-radius: 5px;
  margin: 1vw 2vw;
  font-size: 3.2vw;
  color: #ff4a25;
}

.search-status-text {
  flex: 1;
  margin-right: 1vw;
}

.search-status-clear {
  cursor: pointer;
  font-size: 3vw;
  color: #17BAF9;
}

.no-results {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5vw 0;
  color: #999999;
  font-size: 3.5vw;
}

.no-results-icon {
  font-size: 10vw;
  margin-bottom: 1vw;
}

.no-results-text {
  margin-top: 1vw;
}
</style>