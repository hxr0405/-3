package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.GroupRepository;
import com.HeartStream.heartStreamBack.model.UserGroup;
import com.HeartStream.heartStreamBack.model.GroupMember;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.GroupService;
import com.HeartStream.heartStreamBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImp implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserGroup saveGroup(UserGroup group, Long userId) {
        UserGroup savedGroup = groupRepository.save(group);
        User user = userService.getUserById(userId);
        if (user != null) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(savedGroup);
            groupMember.setUser(user);
        }
        return savedGroup;
    }

    @Override
    public Optional<UserGroup> getGroupById(Long id) {
        return groupRepository.findById(id);
    }
}
