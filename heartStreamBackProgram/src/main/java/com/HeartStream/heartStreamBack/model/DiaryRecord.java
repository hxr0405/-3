package com.HeartStream.heartStreamBack.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DiaryRecord {
    private Long userId;
    private Long diaryId;
    private Date recordTime;
}
