package yun.edu.order.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import yun.edu.order.client.UserClient;
import yun.edu.order.pojo.User;

@Component("clientUserClientFallback")
public class UserClientFallback implements UserClient {
    
    private static final Logger logger = LoggerFactory.getLogger(UserClientFallback.class);
    
    @Override
    public ResponseEntity<User> getUserById(String userId) {
        // 记录日志
        logger.warn("Fallback: 无法从用户服务获取用户信息，使用默认用户信息. userId: {}", userId);
        
        // 返回默认用户信息
        User user = new User();
        user.setUserId(userId);
        user.setUserName("默认用户");
        user.setUserSex(0);
        user.setUserImg("");
        user.setDelTag(0);
        
        return ResponseEntity.ok(user);
    }
} 