package yun.edu.order.feign.fallback;

import org.springframework.stereotype.Component;
import yun.edu.common.Result;
import yun.edu.order.feign.UserClient;

@Component("feignUserClientFallback")
public class UserClientFallback implements UserClient {
    
    @Override
    public Result getUserById(Integer userId) {
        return Result.fail("用户服务不可用，请稍后再试");
    }
} 