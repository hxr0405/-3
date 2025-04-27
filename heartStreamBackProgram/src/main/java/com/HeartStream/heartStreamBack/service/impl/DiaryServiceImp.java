package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.DiaryMapper;
import com.HeartStream.heartStreamBack.model.Comment;
import com.HeartStream.heartStreamBack.model.Diary;
import com.HeartStream.heartStreamBack.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DiaryServiceImp implements DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Override
    public void saveDiary(Diary diary) {
        diary.setCreateTime(new Date());
        diary.setUpdateTime(new Date());
        diaryMapper.insertDiary(diary);
    }

    @Override
    public void updateDiary(Diary diary) {
        diary.setUpdateTime(new Date());
        diaryMapper.updateDiary(diary);
    }

    @Override
    public Diary getDiaryById(Long id) {
        return diaryMapper.getDiaryById(id);
    }

    @Override
    public List<Diary> getPublishedDiaries() {
        return diaryMapper.getPublishedDiaries();
    }

    @Override
    public Diary getRandomDiary() {
        List<Diary> diaries = diaryMapper.getPublishedDiaries();
        if (diaries.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return diaries.get(random.nextInt(diaries.size()));
    }

    @Override
    public void likeDiary(Long diaryId) {
        diaryMapper.incrementLikeCount(diaryId);
    }

    @Override
    public void addComment(Long diaryId, Comment comment) {
        comment.setDiaryId(diaryId);
        diaryMapper.insertComment(comment);
    }

    @Override
    public List<Comment> getCommentsByDiaryId(Long diaryId) {
        return diaryMapper.getCommentsByDiaryId(diaryId);
    }
}
