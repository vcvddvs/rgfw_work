package yun.edu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.common.Result;
import yun.edu.order.feign.fallback.BusinessClientFallback;

@FeignClient(value = "elm-business", fallback = BusinessClientFallback.class)
public interface BusinessClient {
    
    @GetMapping("/business/{businessId}")
    Result getBusinessById(@PathVariable("businessId") Integer businessId);
    
    @GetMapping("/food/{foodId}")
    Result getFoodById(@PathVariable("foodId") Integer foodId);
} 