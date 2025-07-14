package yun.edu.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.common.util.JwtUtil;
import yun.edu.user.mapper.UserMapper;
import yun.edu.user.pojo.User;
import yun.edu.user.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User login(String userId, String password) {
        User user = userMapper.getUserByIdAndPassword(userId, password);
        return user;
    }
    
    @Override
    public Map<String, Object> loginWithToken(String userId, String password) {
        User user = userMapper.getUserByIdAndPassword(userId, password);
        Map<String, Object> result = new HashMap<>();
        
        if (user != null) {
            // 生成JWT令牌
            String token = JwtUtil.generateToken(userId);
            // 只返回token，不返回user对象，避免序列化问题
            result.put("token", token);
            result.put("userId", userId);
            return result;
        }
        
        return null;
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
    
    @Override
    public int saveUser(User user) {
        return userMapper.saveUser(user);
    }
    
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
} 