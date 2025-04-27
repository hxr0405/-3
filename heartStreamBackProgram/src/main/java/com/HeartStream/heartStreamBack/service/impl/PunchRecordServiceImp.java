package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.PunchRecordRepository;
import com.HeartStream.heartStreamBack.model.PunchRecord;
import com.HeartStream.heartStreamBack.service.PunchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PunchRecordServiceImp implements PunchRecordService {
    @Autowired
    private PunchRecordRepository punchRecordRepository;

    @Override
    public PunchRecord savePunchRecord(PunchRecord punchRecord) {
        punchRecord.setPunchTime(new Date());
        return punchRecordRepository.save(punchRecord);
    }
}
