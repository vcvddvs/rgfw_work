// WebConfig.java
package yun.edu.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081") // 前端开发服务器地址
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // 允许携带 Cookie/Token
    }
}