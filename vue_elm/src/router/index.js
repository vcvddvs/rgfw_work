import { createRouter, createWebHistory } from 'vue-router';
import IndexComponent from "@/components/IndexComponent.vue";
import historyComponent from "@/components/historyComponent.vue";
import loginComponent from "@/components/loginComponent.vue";
import businesslistComponent from "@/components/businesslistComponent.vue";
import RegisterComponent from "@/components/RegisterComponent.vue";
import businesslist2Component from "@/components/businesslist2Component.vue";
import paymentComponent from "@/components/paymentComponent.vue";
import OrderComponent from "@/components/OrderComponent.vue";
import addressComponent from "@/components/addressComponent.vue";
import updataComponent from "@/components/updataComponent.vue";
import RatingComponent from "@/components/RatingComponent.vue";

// 定义路由数组
const routes = [
  {
    path: '/',
    redirect: '/Index-Component' // 将根路径重定向到首页
  },
  {
    path: '/login-component',
    name: 'Login',
    component: loginComponent
  },
  {
    path: '/Register-component',
    name: 'Register',
    component: RegisterComponent
  },
  {
    path: '/Index-Component',
    name: 'Index',
    component: IndexComponent
  },
  {
    path: '/business-list2/:businessId/:businessName/:businessImg',
    name: 'BusinessList2',
    component: businesslist2Component
  },
  {
    path: '/business-list',
    component: businesslistComponent
  },
  {
    path: '/order-component/:businessId/:businessName',
    name: 'Order',
    component: OrderComponent,
    props: true
  },
  {
    path: '/history-component',
    component: historyComponent
  },
  {
    path: '/Payment-component',
    name: 'Payment',
    component: paymentComponent
  },
  {
    path: '/address/:userId/:businessId/:businessName',
    name: 'Address',
    component: addressComponent
  },
  {
    path: '/updata-component/:userId',
    name: 'Updata',
    component: updataComponent
  },
  {
    path: '/rating/:orderId/:businessId/:businessName',
    name: 'Rating',
    component: RatingComponent,
    props: true
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;