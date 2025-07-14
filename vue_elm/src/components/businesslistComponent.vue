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
          <!-- ... 其他代码 ... -->
          <div class="text-left-header">
            <h>华莱士.全鸡汉堡</h>
          </div>
          <div class="star">
            <p1>4.7分 </p1>
            <p2> 蜂鸟准时达</p2>
            <p3>约39分钟  月售1000+</p3>
          </div>
          <div class="ticket">
            <img :src="require('@/assets/img/BannerImg/img_1.png')" height="23" width="100"/>
          </div>
          <div class="for-sell">
            <p1>25减8 | 45减15</p1>
            <p2>配送费优惠</p2>
          </div>
        </div>
        <div class="text-picture">
          <img :src="require('@/assets/img/BannerImg/img_2.png')" height="155" width="157"/>
        </div>
      </div>
      <div class="banner">
        <img :src="require('@/assets/img/BannerImg/img_3.png')" height="15" width="100"/>
        <p1>呈贡区汉堡薯条热销榜·第3名></p1>
        <p2>每日更新</p2>
      </div>
    </div>
    <div class="body">
      <!-- ... 其他代码 ... -->
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
      <div class="picture">
        <img :src="require('@/assets/img/BUSINESS/img.png')" height="304" width="1174"/>
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
            <p> 新品尝鲜</p>
            <img :src="require('@/assets/img/BUSINESS/title/img_3.png')" height="13" width="42"/>
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
          <div class="menus">
            <div class="menus-title">
              <p1>学生专享</p1>
              <p2>专属福利，超值推荐</p2>
            </div>
            <div class="menus-body">
              <div class="menus-body-left">
                <img :src="require('@/assets/img/BUSINESS/menus/img.png')" height="124" width="127"/>
              </div>
              <div class="menus-body-right">
                <div class="mbr-title">
                  <p1>香酥鸡腿</p1>
                </div>
                <div class="mbr-banner">
                  <p2>给自己加一个大鸡腿吧~</p2>
                </div>
                <div class="mbr-count">
                  <p3>月售 19</p3>
                </div>
                <div class="mbr-sell">
                  <p4>7.84折</p4>
                  <p5>学生价</p5>
                </div>
                <div class="mbr-price">
                  <p>&#165;10.9</p>
                  <div class="cart-actions">
                    <img
                        v-if="cart['香酥鸡腿'] && cart['香酥鸡腿'].quantity > 0"
                        :src="require('@/assets/img/BUSINESS/menus/img_4.png')"
                        height="27"
                        width="28"
                        @click="decrementQuantity('香酥鸡腿')"
                    />
                    <span v-if="cart['香酥鸡腿'] && cart['香酥鸡腿'].quantity > 0">{{ cart['香酥鸡腿'].quantity }}</span>
                    <img
                        :src="require('@/assets/img/BUSINESS/menus/img_1.png')"
                        height="27"
                        width="28"
                        @click="addToCart('香酥鸡腿', 10.9)"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="menus">
            <div class="menus-title">
              <p1>热销</p1>
              <p2>大家喜欢吃，才叫真好吃</p2>
            </div>
            <div class="menus-body">
              <div class="menus-body-left">
                <img :src="require('@/assets/img/BUSINESS/menus/img_2.png')" height="115" width="127"/>
              </div>
              <div class="menus-body-right">
                <div class="mbr-title">
                  <p1>招牌全鸡自选</p1>
                </div>
                <div class="mbr-banner">
                  <p2>七天内16人下单</p2>
                </div>
                <div class="mbr-count">
                  <p3>月售 71 好评率  93%</p3>

                </div>
                <div class="mbr-sell">
                  <p4>4.8折</p4>

                </div>
                <div class="mbr-price">
                  <p> &#165;26.9</p>
                  <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                </div>
              </div>
            </div>
            <div class="menus">
              <div class="menus-title">
                <p1>优惠</p1>
                <p2>大家喜欢吃，才叫真好吃</p2>
              </div>
              <div class="menus-body">
                <div class="menus-body-left">
                  <img :src="require('@/assets/img/BUSINESS/menus/img_2.png')" height="115" width="127"/>
                </div>
                <div class="menus-body-right">
                  <div class="mbr-title">
                    <p1>招牌全鸡自选</p1>
                  </div>
                  <div class="mbr-banner">
                    <p2>七天内16人下单</p2>
                  </div>
                  <div class="mbr-count">
                    <p3>月售 71 好评率  93%</p3>

                  </div>
                  <div class="mbr-sell">
                    <p4>4.8折</p4>

                  </div>
                  <div class="mbr-price">
                    <p> &#165;26.9</p>
                    <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                  </div>
                </div>
              </div>
              <div class="menus-body">
                <div class="menus-body-left">
                  <img :src="require('@/assets/img/BUSINESS/menus/img_3.png')" height="125" width="129"/>
                </div>
                <div class="menus-body-right">
                  <div class="mbr-title">
                    <p1>香辣鸡腿堡超值套餐</p1>
                  </div>
                  <div class="mbr-banner">
                    <p2>近期三人给出好评</p2>
                  </div>
                  <div class="mbr-count">
                    <p3>月售 200+  好评率95%</p3>
                  </div>
                  <div class="mbr-sell">
                    <p4>5.43折</p4>
                    <p5>学生价</p5>
                  </div>
                  <div class="mbr-price">
                    <p> &#165;23.9</p>
                    <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                  </div>
                </div>
              </div>
              <div class="menus-body">
                <div class="menus-body-left">
                  <img :src="require('@/assets/img/BUSINESS/menus/img.png')" height="115" width="127"/>
                </div>
                <div class="menus-body-right">
                  <div class="mbr-title">
                    <p1>香酥鸡腿</p1>
                  </div>
                  <div class="mbr-banner">
                    <p2>给自己加一个大鸡腿吧~</p2>
                  </div>
                  <div class="mbr-count">
                    <p3>月售 19</p3>
                  </div>
                  <div class="mbr-sell">
                    <p4>7.84折</p4>
                    <p5>学生价</p5>
                  </div>
                  <div class="mbr-price">
                    <p> &#165;10.9</p>
                    <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                  </div>
                </div>
              </div>

            </div>
            <div class="menus">
              <div class="menus-title">
                <p1>学生专享</p1>
                <p2>专属福利，超值推荐</p2>
              </div>
              <div class="menus-body">
                <div class="menus-body-left">
                  <img :src="require('@/assets/img/BUSINESS/menus/img.png')" height="115" width="127"/>
                </div>
                <div class="menus-body-right">
                  <div class="mbr-title">
                    <p1>香酥鸡腿</p1>
                  </div>
                  <div class="mbr-banner">
                    <p2>给自己加一个大鸡腿吧~</p2>
                  </div>
                  <div class="mbr-count">
                    <p3>月售 19</p3>
                  </div>
                  <div class="mbr-sell">
                    <p4>7.84折</p4>
                    <p5>学生价</p5>
                  </div>
                  <div class="mbr-price">
                    <p> &#165;10.9</p>
                    <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="menus">
              <div class="menus-title">
                <p1>学生专享</p1>
                <p2>专属福利，超值推荐</p2>
              </div>
              <div class="menus-body">
                <div class="menus-body-left">
                  <img :src="require('@/assets/img/BUSINESS/menus/img.png')" height="115" width="127"/>
                </div>
                <div class="menus-body-right">
                  <div class="mbr-title">
                    <p1>香酥鸡腿</p1>
                  </div>
                  <div class="mbr-banner">
                    <p2>给自己加一个大鸡腿吧~</p2>
                  </div>
                  <div class="mbr-count">
                    <p3>月售 19</p3>
                  </div>
                  <div class="mbr-sell">
                    <p4>7.84折</p4>
                    <p5>学生价</p5>
                  </div>
                  <div class="mbr-price">
                    <p> &#165;10.9</p>
                    <img :src="require('@/assets/img/BUSINESS/menus/img_1.png')" height="27" width="28"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
    <div class="footer">
      <!-- ... 其他代码 ... -->
      <div class="cart-left">
        <div class="cart-left-icon">
          <i class="fa fa-shopping-cart"></i>
          <div class="car-left-icon-quantity">
          </div>
        </div>
        <div style="margin-top: 15px">价格: {{ totalPrice }}元</div>
        <div class="cart-left-icon-quantity" v-if="cart['香酥鸡腿'] && cart['香酥鸡腿'].quantity > 0">{{ Object.values(cart).reduce((sum, item) => sum + item.quantity, 0) }}</div>
      </div>
      <div class="cart-right">
        <div class="cart-right-item" @click="checkout">去结算</div>
      </div>
    </div>
   </div>
  </div>
</template>


<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const cart = ref({}); // 使用 ref 来创建响应式引用
const router = useRouter();


const totalPrice = computed(() => {
  return Object.values(cart.value).reduce((sum, item) => sum + item.quantity * item.price, 0);
});

function addToCart(itemName, price) {
  if (cart.value[itemName]) {
    cart.value[itemName].quantity += 1;
  } else {
    cart.value[itemName] = { quantity: 1, price: price };
  }
}

function removeFromCart(itemName) {
  if (cart.value[itemName] && cart.value[itemName].quantity >= 1) {
    cart.value[itemName].quantity -= 1;
  }
}

function decrementQuantity(itemName) {
  removeFromCart(itemName);
}

function checkout() {
  alert(`总价：${totalPrice.value}元`);
}

function goBack() {
  router.go(-1);
}
</script>

<style scoped>
.cart-actions {
  display: flex;
  align-items: center;
}

.cart-actions img {
  margin-left: 5px; /* 根据需要调整间距 */
  cursor: pointer;
}
/* ... 将 businessList.html 中的 CSS 样式复制到这里 ... */
.wrapper {
  width: 100%;
  height: 100%;
}

.wrapper .header .title {
  width: 430px;
  height: 260px;
  position: relative;


}

.wrapper .header .title .img2{
  width: 100%;
  height: 100%;


}

.wrapper .header .title .img1{
  position: relative;
  z-index: 1;
  top: 88px; /* 你可以根据需要调整垂直位置 */
  left: 28px; /* 你可以根据需要调整水平位置 */
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
  top: 0; /* 指定元素在屏幕上固定时距离顶部的位置 */

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
  border: #33b56c 1px solid;
}
.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-price p {
  font-weight: bolder;
  color: red;
  border: #33b56c 1px solid;
}

.wrapper .body .foodList .foodList-right .menus .menus-body .menus-body-right .mbr-price img {
 border: 1px solid black;
}



/**购物车footer部分*/
.wrapper .footer{
  width: 100%;
  height: 14vw;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
}

.wrapper .footer .cart-left{
  flex: 2;
  background-color:white;
  display: flex;
}

.wrapper .footer .cart-left .cart-left-icon{
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

.wrapper .footer .cart-left .cart-left-icon-quantity{
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

.wrapper .footer .cart-left .cart-left-info p{
  font-size: 4.5vw;
  color: black;
  margin:1vw 1vw 1vw 1vw;

}

.wrapper .footer .cart-left .cart-left-info p:last-child{
  font-size: 3.8vw;
  color: #AAA;
  margin-top: 0;
}



.wrapper .footer .cart-right{
  flex:1;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

/*够起送费时*/
.wrapper .footer .cart-right .cart-right-item{
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
/*不够起送费时
.wrapper .footer .cart-right .cart-right-item{
    background-color: #535356;
    width: 100%;
    height: 100%;
    color: white;
    font-weight: 700;
    user-select: none;


    display: flex;
    justify-content: center;
    align-items: center;
}*/


</style>