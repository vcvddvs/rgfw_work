package yun.edu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.common.Result;
import yun.edu.order.feign.fallback.DeliveryAddressClientFallback;

@FeignClient(value = "elm-delivery", fallback = DeliveryAddressClientFallback.class)
public interface DeliveryAddressClient {
    
    @GetMapping("/deliveryAddress/{daId}")
    Result getDeliveryAddressById(@PathVariable("daId") Integer daId);
} 