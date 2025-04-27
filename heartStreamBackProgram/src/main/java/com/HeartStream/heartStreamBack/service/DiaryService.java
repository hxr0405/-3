package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.Comment;
import com.HeartStream.heartStreamBack.model.Diary;

import java.util.List;

public interface DiaryService {
    void saveDiary(Diary diary);
    void updateDiary(Diary diary);
    Diary getDiaryById(Long id);
    List<Diary> getPublishedDiaries();
    Diary getRandomDiary();
    void likeDiary(Long diaryId);
    void addComment(Long diaryId, Comment comment);
    List<Comment> getCommentsByDiaryId(Long diaryId);
}
