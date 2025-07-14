package yun.edu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.common.Result;
import yun.edu.order.feign.fallback.UserClientFallback;

@FeignClient(value = "elm-user", fallback = UserClientFallback.class)
public interface UserClient {
    
    @GetMapping("/user/{userId}")
    Result getUserById(@PathVariable("userId") Integer userId);
} 