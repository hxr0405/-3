package com.HeartStream.heartStreamBack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Boolean isPublished;
    private Date createTime;
    private Date updateTime;
    private Long likeCount;
    private List<Comment> comments;
}
