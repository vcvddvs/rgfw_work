package yun.edu.order.feign.fallback;

import org.springframework.stereotype.Component;
import yun.edu.common.Result;
import yun.edu.order.feign.DeliveryAddressClient;

@Component("feignDeliveryAddressClientFallback")
public class DeliveryAddressClientFallback implements DeliveryAddressClient {
    
    @Override
    public Result getDeliveryAddressById(Integer daId) {
        return Result.fail("配送地址服务不可用，请稍后再试");
    }
} 