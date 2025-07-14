package yun.edu.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.common.pojo.Result;
import yun.edu.order.client.fallback.BusinessClientFallback;
import yun.edu.order.config.FeignConfig;
import yun.edu.order.pojo.Business;
import yun.edu.order.pojo.Food;

@FeignClient(value = "elm-business", fallback = BusinessClientFallback.class, configuration = FeignConfig.class)
public interface BusinessClient {
    
    @GetMapping(value = "/business/{businessId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Result<Business>> getBusinessById(@PathVariable("businessId") Integer businessId);
    
    @GetMapping(value = "/food/{foodId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Result<Food>> getFoodById(@PathVariable("foodId") Integer foodId);
} 