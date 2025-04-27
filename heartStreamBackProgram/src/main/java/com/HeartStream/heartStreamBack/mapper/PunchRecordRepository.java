package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.PunchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunchRecordRepository extends JpaRepository<PunchRecord, Long> {
}
