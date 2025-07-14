package yun.edu.user.service;

import yun.edu.user.pojo.User;

import java.util.Map;

public interface UserService {
    User login(String userId, String password);
    
    User getUserById(String userId);
    
    User getUserByUsername(String username);
    
    int saveUser(User user);
    
    int updateUser(User user);

    Map<String, Object> loginWithToken(String userId, String password);
} 