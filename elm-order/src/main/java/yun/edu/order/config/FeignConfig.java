package yun.edu.order.config;

import feign.Logger;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeignConfig {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(feignHttpMessageConverter());
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverter<?> jacksonConverter = new MappingJackson2HttpMessageConverter();
        
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        
        ((MappingJackson2HttpMessageConverter) jacksonConverter).setSupportedMediaTypes(mediaTypes);
        
        return () -> new HttpMessageConverters(jacksonConverter);
    }

    public class CustomErrorDecoder implements ErrorDecoder {
        private final ErrorDecoder defaultErrorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            logger.error("Feign client error: status={}, methodKey={}", response.status(), methodKey);
            
            // 如果是500错误，并且是来自用户服务的请求，我们可以选择不抛出异常，让fallback处理
            if (response.status() == 500 && methodKey.contains("UserClient")) {
                logger.warn("User service error, using fallback");
                return new RuntimeException("User service error, using fallback");
            }
            
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }
} 