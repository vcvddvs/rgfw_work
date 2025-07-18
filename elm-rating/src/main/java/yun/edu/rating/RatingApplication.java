package yun.edu.rating;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("yun.edu.rating.mapper")
public class RatingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }
} 