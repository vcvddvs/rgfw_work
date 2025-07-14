package yun.edu.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yun.edu.order.interceptor.AuthenticationInterceptor;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/cart/**", "/orders/**")  // 需要认证的路径
                .excludePathPatterns("/cart/user/**");      // 不需要认证的路径
    }
} 