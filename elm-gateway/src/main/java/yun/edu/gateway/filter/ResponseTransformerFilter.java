package yun.edu.gateway.filter;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class ResponseTransformerFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        // 合并所有的DataBuffer
                        DataBuffer joinedBuffer = bufferFactory.join(dataBuffers);
                        byte[] content = new byte[joinedBuffer.readableByteCount()];
                        joinedBuffer.read(content);
                        DataBufferUtils.release(joinedBuffer);
                        
                        // 将响应转换为字符串
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        
                        // 如果需要，在这里转换响应格式
                        // 例如，将新格式转换为旧格式
                        // responseBody = transformResponse(responseBody);
                        
                        // 将转换后的响应写回
                        byte[] uppedContent = responseBody.getBytes(StandardCharsets.UTF_8);
                        return bufferFactory.wrap(uppedContent);
                    }).flatMap(Flux::just));
                }
                return super.writeWith(body);
            }
        };
        
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
    
    @Override
    public int getOrder() {
        return -2; // 在JWT过滤器之后执行
    }
    
    // 如果需要转换响应格式，可以在这里实现
    private String transformResponse(String responseBody) {
        // 根据需要转换响应格式
        return responseBody;
    }
} 