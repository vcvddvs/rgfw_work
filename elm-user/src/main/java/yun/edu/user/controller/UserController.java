package yun.edu.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.common.pojo.Result;
import yun.edu.user.pojo.User;
import yun.edu.user.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> loginInfo) {
        String userId = loginInfo.get("userId");
        String password = loginInfo.get("password");
        Map<String, Object> loginResult = userService.loginWithToken(userId, password);
        
        if (loginResult != null && loginResult.containsKey("token")) {
            return Result.success(loginResult);
        } else {
            return Result.failure("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public int register(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/")
    public int updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
} 