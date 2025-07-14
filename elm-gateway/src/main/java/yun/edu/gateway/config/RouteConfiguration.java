package yun.edu.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 用户服务路由
                .route("user_service", r -> r.path("/user/**")
                        .uri("lb://elm-user"))
                // 兼容原有前端API路径
                .route("user_service_compat", r -> r.path("/api/user/**")
                        .filters(f -> f.rewritePath("/api/user/(?<segment>.*)", "/user/${segment}"))
                        .uri("lb://elm-user"))
                
                // 商家服务路由
                .route("business_service", r -> r.path("/business/**", "/food/**")
                        .uri("lb://elm-business"))
                // 兼容原有前端API路径
                .route("business_service_compat", r -> r.path("/api/business/**", "/api/food/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://elm-business"))
                
                // 订单服务路由
                .route("order_service", r -> r.path("/orders/**", "/cart/**")
                        .uri("lb://elm-order"))
                // 兼容原有前端API路径
                .route("order_service_compat", r -> r.path("/api/orders/**", "/api/cart/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://elm-order"))
                
                // 配送地址服务路由
                .route("delivery_service", r -> r.path("/deliveryAddress/**")
                        .uri("lb://elm-delivery"))
                // 兼容原有前端API路径
                .route("delivery_service_compat", r -> r.path("/api/deliveryAddress/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://elm-delivery"))
                
                // 支付服务路由
                .route("payment_service", r -> r.path("/payment/**")
                        .uri("lb://elm-payment"))
                // 兼容原有前端API路径
                .route("payment_service_compat", r -> r.path("/api/payment/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://elm-payment"))
                
                // 评价服务路由
                .route("rating_service", r -> r.path("/rating/**")
                        .uri("lb://elm-rating"))
                // 兼容原有前端API路径
                .route("rating_service_compat", r -> r.path("/api/rating/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://elm-rating"))
                
                .build();
    }
} 