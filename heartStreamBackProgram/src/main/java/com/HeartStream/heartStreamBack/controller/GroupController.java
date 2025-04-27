package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.GroupMember;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.model.UserGroup;
import com.HeartStream.heartStreamBack.service.GroupMemberService;
import com.HeartStream.heartStreamBack.service.GroupService;
import com.HeartStream.heartStreamBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/{userId}/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping
    public ResponseEntity<UserGroup> createGroup(@RequestBody UserGroup group, @PathVariable("userId") Long userId) {
        UserGroup savedGroup = groupService.saveGroup(group, userId);
        User user = userService.getUserById(userId);
        GroupMember groupMember = new GroupMember();
        groupMember.setUser(user);
        groupMember.setGroup(savedGroup);
        groupMemberService.saveGroupMember(groupMember);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getGroupById(@PathVariable("id") Long id) {
        Optional<UserGroup> group = groupService.getGroupById(id);
        return group.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
