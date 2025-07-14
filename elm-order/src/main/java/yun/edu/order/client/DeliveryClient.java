package yun.edu.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yun.edu.common.pojo.Result;
import yun.edu.order.client.fallback.DeliveryClientFallback;
import yun.edu.order.config.FeignConfig;
import yun.edu.order.pojo.DeliveryAddress;

@FeignClient(value = "elm-delivery", fallback = DeliveryClientFallback.class, configuration = FeignConfig.class)
public interface DeliveryClient {
    
    @GetMapping(value = "/deliveryAddress/{daId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Result<DeliveryAddress> getDeliveryAddressById(@PathVariable("daId") Integer daId);
} 