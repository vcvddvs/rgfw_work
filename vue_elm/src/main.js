// main.js
import { createApp } from 'vue';
import App from '@/App.vue';
import '@fortawesome/fontawesome-free/css/all.css';
import router from '@/router/index.js'; // 路由文件已更新为 Vue Router 4

// 引入 Element Plus 和 Element Plus 的样式
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

// 创建 Vue 应用实例
const app = createApp(App);

// 使用 Element Plus
app.use(ElementPlus);

// 使用 Vue Router
app.use(router);

// 挂载应用实例
app.mount('#app');