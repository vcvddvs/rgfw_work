<template>
  <div class="wrapper">
    <div class="header">
      <div class="title">
        <div class="p1">
          <span class="iconfont icon-back" style="font-size: 8vw" @click="goBack"></span>
        </div>
        <div class="p2">
          <p>在线支付</p>
        </div>
      </div>
    </div>
    <h3>订单信息</h3>
    <div class="orders-info">
      <p>{{ businessName }}</p>
      <p>¥{{ totalPrice.toFixed(2) }}</p>
    </div>
    <ul class="payment-type">
      <li @click="selectPaymentMethod('alipay')">
        <img :src="require('@/assets/img/alipay.png')" height="32" width="120"/>
        <i v-if="selectedMethod === 'alipay'" class="fa fa-check-circle"></i>
      </li>
      <li @click="selectPaymentMethod('wechat')">
        <img :src="require('@/assets/img/wechat.png')" height="32" width="120"/>
        <i v-if="selectedMethod === 'wechat'" class="fa fa-check-circle"></i>
      </li>
    </ul>
    <div class="payment-button">
      <button @click="confirmPayment">确认支付</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const businessName = ref(route.query.businessName || '未知店铺');

// 使用computed保证响应性，并且确保totalPrice是数字类型
const totalPrice = computed(() => {
  return route.query.totalAmount ? parseFloat(route.query.totalAmount) : 0;
});

const selectedMethod = ref(null); // 用于跟踪选中的支付方式

function selectPaymentMethod(method) {
  selectedMethod.value = method; // 设置选中的支付方式
}

function confirmPayment() {
  alert(`总价：¥${totalPrice.value.toFixed(2)}，支付成功，使用${selectedMethod.value || '未知支付方式'}支付`);
}

function goBack() {
  window.history.back(); // 使用window.history.back()替代router.go(-1)
}
</script>

<style scoped>
.wrapper{
  width: 100%;
  height: 100%;
}


.wrapper .header .title{
  display: flex;
  align-items: center;
  background-color: red;
  text-align: center;
  height: 18vw;
  position: fixed;
  left: 0;
  top: 0;
  right: 0;


}

.wrapper .header .title .p1{
  flex: 1;
  color: white;

}


.wrapper .header .title .p2{
  flex: 9;
  color: white;
  font-size: 4.5vw;
  padding-right: 6vw;
}

/*订单信息*/
.wrapper h3{
  box-sizing: border-box;
  padding: 4vw 4vw 0;
  margin-top: 18vw;
  font-size: 4vw;
  font-weight: 300;
  color: #9F9F9F;
}

.wrapper .orders-info{
  box-sizing: border-box;
  padding: 4vw;
  font-size: 4vw;
  color: #666;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .orders-info p:last-child{
  color: orangered;
}

/*订单明细*/
.wrapper .order-detailet{
  width: 100%;
  padding-left: 0;
}

.wrapper .order-detailet li{
  margin: 0 auto;
  width: 90%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .order-detailet li p{
  font-size: 3vw;
  color: #666666;
}

/*支付方式*/
.wrapper .payment-type{
  width: 100%;
  padding-left: 0;
}

.wrapper .payment-type li{
  width: 90%;
  padding:4vw;

  display: flex;
  align-items: center;
  justify-content: space-between;
}

.wrapper .payment-type li img{
  width: 33vw;
  height: 8.9vw;
}

.wrapper .payment-type li .fa-check-circle{
  font-size: 5vw;
  color: #38ca73;
}


.wrapper .payment-button{
  width: 90%;
  box-sizing: border-box;
  padding:4vw;
  outline: none;
  margin-left: 4vw;
}

.wrapper .payment-button button{
  width: 100%;
  height: 10vw;
  margin: 0 auto;
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #38ca73;
  color: white;
}
</style>