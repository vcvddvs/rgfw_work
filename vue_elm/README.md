# vue_elm

## 项目说明
饿了么前端项目，基于Vue 3开发，适配Spring Cloud微服务架构后端。

## 微服务适配说明
此版本已适配Spring Cloud微服务架构，所有API请求通过API网关（http://localhost:8082）进行路由。

主要更新：
1. 添加统一API请求工具类（src/utils/api.js）
2. 所有组件API请求已更新为使用新的API工具类
3. 配置Vue代理，指向API网关

## 项目设置
```
# 安装依赖
npm install

# 开发模式运行
npm run serve

# 生产环境构建
npm run build

# 代码检查
npm run lint
```

## 注意事项
- 确保后端微服务已启动，特别是API网关服务
- 默认API网关地址为http://localhost:8082，可在api.js中修改

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
