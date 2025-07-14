package yun.edu.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.order.client.fallback.UserClientFallback;
import yun.edu.order.config.FeignConfig;
import yun.edu.order.pojo.User;

@FeignClient(value = "elm-user", fallback = UserClientFallback.class, configuration = FeignConfig.class)
public interface UserClient {
    
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getUserById(@PathVariable("userId") String userId);
} 