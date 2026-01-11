package org.example.novelverse.controller;

import org.example.novelverse.domain.User;
import org.example.novelverse.service.UserService;
import org.example.novelverse.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        //查的是有id的
        User userTrue = userService.getByName(user.getName());

        if (userTrue == null) {
            return new Result(Code.LOGIN_ERROR, "用户不存在");
        }
        if (!user.getPassword().equals(userTrue.getPassword())) {
            return new Result(Code.LOGIN_ERROR, "密码错误");
        }

        String token = JwtUtil.generateToken(userTrue.getId(), userTrue.getName());

        return new Result(Code.LOGIN_OK, token);
    }


    @PostMapping("/register")
    public Result registerUser(@RequestBody User user) {
        userService.insert(user);
        //注册没id就查
        if (user.getId() == null) {
            user.setId(userService.getByName(user.getName()).getId());
        }
        String token = JwtUtil.generateToken(user.getId(), user.getName());
        return new Result(Code.INSERT_OK, token);
    }
}
