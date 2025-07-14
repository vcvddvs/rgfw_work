package yun.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yun.edu.pojo.User;
import yun.edu.pojo.Result;
import yun.edu.service.UserService;
import yun.edu.utils.JwtUtil;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired  // 使用注入的JwtUtil
    private JwtUtil jwtUtil;  // 移除方法内的new JwtUtil()

    @PostMapping("elm_servlet_war_exploded/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println("登录请求用户: " + user.getUserId());
        User authenticatedUser = userService.login(user.getUserId(), user.getPassword());
        if (authenticatedUser != null) {
            String jwt = jwtUtil.generateToken(authenticatedUser);  // 直接使用注入的jwtUtil
            return ResponseEntity.ok(Result.success(jwt));
        } else {
            return ResponseEntity.status(401).body(Result.failure("用户名或密码错误"));
        }
    }


    @PostMapping("elm_servlet_war_exploded/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean isRegistered = userService.register(user);
        if (isRegistered) {
            return ResponseEntity.ok(Result.success("注册成功"));
        } else {
            return ResponseEntity.status(400).body(Result.failure("注册失败，用户名已存在"));
        }
    }
}