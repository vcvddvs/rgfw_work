package yun.edu.service;

import yun.edu.pojo.User;

public interface UserService {
    User login(String userId, String password);

    boolean register(User user);
}