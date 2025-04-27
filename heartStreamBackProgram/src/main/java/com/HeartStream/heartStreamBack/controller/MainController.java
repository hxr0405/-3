package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.MusicPlayer;
import com.HeartStream.heartStreamBack.model.MusicRecord;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.MusicService;
import com.HeartStream.heartStreamBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/main")
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicPlayer musicPlayer;

    //主界面服务
    @PostMapping("/mainView")
    public void mainView(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        log.info("用户{}进入主界面", user.getUsername());
    }
}

