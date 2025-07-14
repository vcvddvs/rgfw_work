package yun.edu.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseTransformFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    public ResponseTransformFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    MediaType contentType = originalResponse.getHeaders().getContentType();
                    if (contentType != null && contentType.includes(MediaType.APPLICATION_JSON)) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            // 合并所有DataBuffer
                            DataBuffer dataBuffer = bufferFactory.join(dataBuffers);
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            
                            // 转换响应
                            String responseBody = new String(content, StandardCharsets.UTF_8);
                            String transformedResponse = transformResponse(responseBody);
                            
                            // 创建新的DataBuffer
                            byte[] transformedContent = transformedResponse.getBytes(StandardCharsets.UTF_8);
                            originalResponse.getHeaders().setContentLength(transformedContent.length);
                            return bufferFactory.wrap(transformedContent);
                        }).flatMap(Flux::just));
                    }
                }
                return super.writeWith(body);
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    private String transformResponse(String originalResponse) {
        try {
            // 尝试解析原始响应
            Object responseObj = objectMapper.readValue(originalResponse, Object.class);
            
            // 如果已经是Result格式，则不转换
            if (isResultFormat(responseObj)) {
                return originalResponse;
            }
            
            // 创建Result格式的响应
            Map<String, Object> result = new HashMap<>();
            result.put("code", 1);
            result.put("msg", "success");
            result.put("data", responseObj);
            
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            // 如果解析失败，返回原始响应
            return originalResponse;
        }
    }

    private boolean isResultFormat(Object obj) {
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.containsKey("code") && map.containsKey("msg") && map.containsKey("data");
        }
        return false;
    }

    @Override
    public int getOrder() {
        // 在最后执行，确保其他过滤器都已完成
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
} 