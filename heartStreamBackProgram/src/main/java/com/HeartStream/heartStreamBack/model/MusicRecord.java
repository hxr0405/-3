package com.HeartStream.heartStreamBack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRecord {
    private Long userId;
    private Long musicId;
    private Date date;
}
