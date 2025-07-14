package yun.edu.order.feign.fallback;

import org.springframework.stereotype.Component;
import yun.edu.common.Result;
import yun.edu.order.feign.BusinessClient;

@Component("feignBusinessClientFallback")
public class BusinessClientFallback implements BusinessClient {
    
    @Override
    public Result getBusinessById(Integer businessId) {
        return Result.fail("商家服务不可用，请稍后再试");
    }
    
    @Override
    public Result getFoodById(Integer foodId) {
        return Result.fail("商家服务不可用，请稍后再试");
    }
} 