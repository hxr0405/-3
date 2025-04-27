package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.UserGroup;

import java.util.Optional;

public interface GroupService {
    UserGroup saveGroup(UserGroup group, Long userId);

    Optional<UserGroup> getGroupById(Long id);
}
