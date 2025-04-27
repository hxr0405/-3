package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.GroupMemberRepository;
import com.HeartStream.heartStreamBack.model.GroupMember;
import com.HeartStream.heartStreamBack.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberServiceImp implements GroupMemberService {
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Override
    public GroupMember saveGroupMember(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public List<GroupMember> getGroupMemberByGroupId(Long groupId, Long userId) {
        return groupMemberRepository.findByGroupIdAndUserId(groupId);
    }
}
