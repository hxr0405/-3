package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.UserGroup;
import com.HeartStream.heartStreamBack.model.GroupMember;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.GroupMemberService;
import com.HeartStream.heartStreamBack.service.GroupService;
import com.HeartStream.heartStreamBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userId}/groupMembers")
public class GroupMemberController {
    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<GroupMember> createGroupMember(@RequestBody GroupMember groupMember) {
        GroupMember savedGroupMember = groupMemberService.saveGroupMember(groupMember);
        return new ResponseEntity<>(savedGroupMember, HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<GroupMember> joinGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        GroupMember groupMember = new GroupMember();
        User user = userService.getUserById(userId);
        UserGroup group = groupService.getGroupById(groupId).get();
        groupMember.setUser(user);
        groupMember.setGroup(group);
        GroupMember savedGroupMember = groupMemberService.saveGroupMember(groupMember);
        return new ResponseEntity<>(savedGroupMember, HttpStatus.CREATED);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<GroupMember>> getGroupMemberByGroupId(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        List<GroupMember> groupMember = groupMemberService.getGroupMemberByGroupId(groupId, userId);
        return new ResponseEntity<>(groupMember, HttpStatus.OK);
    }
}
