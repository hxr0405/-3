package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Result;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.UserService;
import com.HeartStream.heartStreamBack.utils.OSSUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OSSUtil ossUtil;

    // 修改用户信息
    @PostMapping("/edit")
    public Result edit(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        log.info("用户修改：{}", user);
        if (userService.edit(user)) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    // 修改用户头像
    @PostMapping("/editImage")
    public Result editImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String imageUrl = userService.uploadUserImage(userId, file);
        if (imageUrl != null) {
            return Result.success("头像修改成功");
        } else {
            return Result.error("头像修改失败");
        }
    }

    // 获取用户头像
    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String image = user.getImage();// 构建 OSS 对象名称
        OSS ossClient =ossUtil.getOSSClient();
        String bucketName = "heart-stream"; // 替换为你的 OSS bucket 名称

        OSSObject ossObject = ossClient.getObject(bucketName, image);
        InputStream inputStream = ossObject.getObjectContent();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(new InputStreamResource(inputStream));
    }
}
