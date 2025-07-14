package yun.edu.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:elm-cloud-secret-key-for-jwt-token-generation-and-validation}")
    private String jwtSecret;
    
    // 不需要验证的路径
    private static final List<String> WHITELIST = Arrays.asList(
            "/user/login",
            "/user/register",
            "/business/list",
            "/business/",
            "/food/"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // 检查是否是白名单路径
        if (isWhiteListPath(path)) {
            return chain.filter(exchange);
        }
        
        // 获取token
        String token = getTokenFromRequest(request);
        
        // 验证token
        if (!StringUtils.hasText(token) || !validateToken(token)) {
            return onError(exchange, "未授权访问", HttpStatus.UNAUTHORIZED);
        }
        
        // 将用户信息传递给下游服务
        String userId = getUserIdFromToken(token);
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .build();
        
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isWhiteListPath(String path) {
        return WHITELIST.stream().anyMatch(prefix -> path.startsWith(prefix));
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        String body = "{\"code\":0,\"msg\":\"" + message + "\",\"data\":null}";
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        // 在响应转换过滤器之前执行
        return Ordered.HIGHEST_PRECEDENCE;
    }
} 