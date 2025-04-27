package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.PunchRecord;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.model.UserGroup;
import com.HeartStream.heartStreamBack.service.GroupService;
import com.HeartStream.heartStreamBack.service.PunchRecordService;
import com.HeartStream.heartStreamBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{userId}/punchRecord/{groupId}")
public class PunchRecordController {
    @Autowired
    private PunchRecordService punchRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService userGroupService;

    @PostMapping
    public ResponseEntity<PunchRecord> createPunchRecord(@RequestBody PunchRecord punchRecord, @PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId) {
        User user = userService.getUserById(userId);
        UserGroup group = userGroupService.getGroupById(groupId).get();
        punchRecord.setUser(user);
        punchRecord.setGroup(group);
        PunchRecord savedPunchRecord = punchRecordService.savePunchRecord(punchRecord);
        return new ResponseEntity<>(savedPunchRecord, HttpStatus.CREATED);
    }
}
