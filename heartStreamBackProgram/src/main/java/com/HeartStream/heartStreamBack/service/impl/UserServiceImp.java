package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.UserMapper;
import com.HeartStream.heartStreamBack.model.LoginInfo;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.UserService;
import com.HeartStream.heartStreamBack.utils.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Override
    public LoginInfo login(User user) {
        User u = userMapper.selectByUsernameAndPassword(user);
        if(u != null){
            log.info("登录成功");
            return new LoginInfo(u.getID(),u.getUsername());
        }
        log.info("登录失败");
        return null;
    }

    @Override
    public boolean register(User user) {
        if (userMapper.countByUsername(user.getUsername()) > 0) {
            log.info("用户名已存在");
            return false;
        }

        if (!isValidPassword(user.getPassword())) {
            log.info("密码格式不正确");
            return false;
        }

        if (!isValidEmail(user.getEmail())) {
            log.info("邮箱格式不正确");
            return false;
        }

        try {
            int result = userMapper.insert(user);
            if (result > 0) {
                log.info("注册成功");
                return true;
            } else {
                log.info("注册失败");
                return false;
            }
        } catch (Exception e) {
            log.error("注册异常", e);
            return false;
        }
    }

    @Override
    public boolean forget(User user) {
        if (userMapper.countByUsernameAndEmail(user.getUsername(), user.getEmail()) <= 0) {
            log.info("用户名或邮箱不正确");
            return false;
        }
        log.info("找回密码成功");
        return true;
    }

    @Override
    public boolean updatePassword(User user) {
        if (!isValidPassword(user.getPassword())) {
            log.info("修改密码格式不正确");
            return false;
        }
        try {
            int result = userMapper.updatePassword(user);
            if (result > 0) {
                log.info("修改密码成功");
                return true;
            } else {
                log.info("修改密码失败");
                return false;
            }
        } catch (Exception e) {
            log.error("修改密码异常", e);
            return false;
        }
    }

    @Override
    public boolean edit(User user) {
        try {
            int result = userMapper.updateUser(user);
            if (result > 0) {
                log.info("修改用户信息成功");
                return true;
            }else {
                log.info("修改用户信息失败");
                return false;
            }
        }catch (Exception e) {
            log.error("修改用户信息异常", e);
            return false;
        }
    }

    @Override
    public boolean editImage(User user) {
        try {
            int result = userMapper.updateImage(user);
            if (result > 0) {
                log.info("修改用户头像成功");
                return true;
            }else {
                log.info("修改用户头像失败");
                return false;
            }
        }catch (Exception e) {
            log.error("修改用户头像异常", e);
            return false;
        }
    }

    @Override
    public User getUserById(Long userId) {
        User user = userMapper.getUserById(userId);
        if (user != null) {
            log.info("获取用户信息成功");
            return user;
        }
        return null;
    }

    @Override
    public String uploadUserImage(Long userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String bucketName = "heart-stream"; // 替换为你的 OSS bucket 名称
        String objectName = "userImage/" + userId + "_" + file.getOriginalFilename();

        // 上传文件到OSS
        String imageUrl = fileService.uploadFile(bucketName, objectName, file);

        if (imageUrl != null) {
            // 更新数据库中的image字段
            User user = userMapper.getUserById(userId);
            user.setImage(objectName);
            userMapper.updateImage(user);
        }

        return imageUrl;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailPattern);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordPattern);
    }
}
