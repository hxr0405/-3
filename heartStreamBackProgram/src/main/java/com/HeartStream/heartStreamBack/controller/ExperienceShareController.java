package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.ExperienceShare;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.model.UserGroup;
import com.HeartStream.heartStreamBack.service.ExperienceShareService;
import com.HeartStream.heartStreamBack.service.GroupService;
import com.HeartStream.heartStreamBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/{userId}/experiences/{groupId}")
public class ExperienceShareController {
    @Autowired
    private ExperienceShareService experienceShareService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ExperienceShare> createExperienceShare(@RequestBody ExperienceShare experienceShare ,@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId) {
        User user = userService.getUserById(userId);
        UserGroup group = groupService.getGroupById(groupId).get();
        log.info("user:{}",user);
        log.info("group:{}",group);
        experienceShare.setUser(user);
        experienceShare.setGroup(group);
        ExperienceShare savedExperienceShare = experienceShareService.saveExperienceShare(experienceShare);
        return new ResponseEntity<>(savedExperienceShare, HttpStatus.CREATED);
    }
}
