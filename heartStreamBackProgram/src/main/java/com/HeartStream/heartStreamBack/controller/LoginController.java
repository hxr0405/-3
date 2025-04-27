package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.LoginInfo;
import com.HeartStream.heartStreamBack.model.Result;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    // 登录服务
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("用户登录：{}", user);
        LoginInfo loginInfo = userService.login(user);
        if (loginInfo == null) {
            return Result.error("账号或密码错误");
        } else {
            return Result.success(loginInfo);
        }
    }

    // 注册服务
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.info("用户注册：{}", user);
        boolean flag = userService.register(user);
        if (flag) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败");
        }
    }

    //找回密码服务
    @PostMapping("/forget")
    public Result forget(@RequestBody User user) {
        log.info("用户找回密码：{}", user);
        boolean flag = userService.forget(user);
        if (flag) {
            return Result.success("找回密码成功");
        } else {
            return Result.error("找回密码失败");
        }
    }

    // 修改密码服务
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody User user) {
        log.info("用户修改密码：{}", user);
        boolean flag = userService.updatePassword(user);
        if (flag) {
            return Result.success("修改密码成功");
        } else {
            return Result.error("修改密码失败");
        }
    }
}
