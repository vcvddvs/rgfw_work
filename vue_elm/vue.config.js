const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8082', // 指向API网关
        changeOrigin: true,
        pathRewrite: {
          '^/api': '' // 去掉请求路径中的/api前缀
        }
      }
    }
  }
})
// vue.config.js
