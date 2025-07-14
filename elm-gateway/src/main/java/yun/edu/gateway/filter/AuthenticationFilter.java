package yun.edu.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import yun.edu.common.util.JwtUtil;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final List<String> PUBLIC_PATHS = List.of(
        "/user/login",
        "/user/register",
        "/business/list",
        "/business/",
        "/food/",
        "/cart/user/"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        
        // 检查是否是公开路径
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // 获取Authorization头
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        
        if (authHeader != null && !authHeader.isEmpty()) {
            String token = authHeader.get(0);
            
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
                
                try {
                    // 验证token
                    if (JwtUtil.validateToken(token)) {
                        // 将用户ID添加到请求头中
                        String userId = JwtUtil.extractUserId(token);
                        ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("X-User-ID", userId)
                            .build();
                        return chain.filter(exchange.mutate().request(request).build());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 认证失败
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -100; // 确保在其他过滤器之前执行
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> path.startsWith(publicPath));
    }
} 