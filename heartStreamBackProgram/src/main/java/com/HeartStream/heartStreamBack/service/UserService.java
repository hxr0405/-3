package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.LoginInfo;
import com.HeartStream.heartStreamBack.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //登录
    LoginInfo login(User user);

    //注册
    boolean register(User user);

    //忘记密码
    boolean forget(User user);

    //修改密码
    boolean updatePassword(User user);

    //修改用户信息
    boolean edit(User user);

    //修改用户头像
    boolean editImage(User user);

    User getUserById(Long userId);

    String uploadUserImage(Long userId, MultipartFile file);
}
