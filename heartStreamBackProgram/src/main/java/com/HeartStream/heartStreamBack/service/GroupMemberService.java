package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.GroupMember;

import java.util.List;

public interface GroupMemberService {
    GroupMember saveGroupMember(GroupMember groupMember);

    List<GroupMember> getGroupMemberByGroupId(Long groupId, Long userId);
}
