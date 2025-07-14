package yun.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.mapper.LoginMapper;
import yun.edu.pojo.User;
import yun.edu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User login(String userId, String password) {
        return loginMapper.getPandW(userId, password);
    }

    @Override
    public boolean register(User user) {
        User existingUser = loginMapper.getPandW(user.getUserId(), null);
        if (existingUser != null) {
            return false; // 用户名已存在
        }
        loginMapper.insertUser(user);
        // 保存用户信息到数据库
        return true;
    }
}