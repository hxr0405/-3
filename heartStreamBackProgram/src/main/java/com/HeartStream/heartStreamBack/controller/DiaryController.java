package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Comment;
import com.HeartStream.heartStreamBack.model.Diary;
import com.HeartStream.heartStreamBack.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/{userId}/diaries")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    // 创建日记
    @PostMapping
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary, @PathVariable Long userId) {
        diary.setUserId(userId);
        diaryService.saveDiary(diary);
        return new ResponseEntity<>(diary, HttpStatus.CREATED);
    }

    // 更新日记
    @PutMapping("/{id}")
    public ResponseEntity<Diary> updateDiary(@PathVariable("userId") Long userId,@PathVariable("id") Long id, @RequestBody Diary diary) {
        diary.setUserId(userId);
        diary.setId(id);
        diaryService.updateDiary(diary);
        return new ResponseEntity<>(diary, HttpStatus.OK);
    }

    // 获取单个日记
    @GetMapping("/{id}")
    public ResponseEntity<Diary> getDiaryById(@PathVariable Long id) {
        Diary diary = diaryService.getDiaryById(id);
        if (diary != null) {
            return new ResponseEntity<>(diary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 获取所有已发布的日记
    @GetMapping("/published")
    public ResponseEntity<List<Diary>> getPublishedDiaries() {
        List<Diary> diaries = diaryService.getPublishedDiaries();
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    // 获取随机日记
    @GetMapping("/random")
    public ResponseEntity<Diary> getRandomDiary() {
        Diary randomDiary = diaryService.getRandomDiary();
        if (randomDiary!= null) {
            return new ResponseEntity<>(randomDiary, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 获取下一篇日记
    @GetMapping("/next/random/{currentId}")
    public ResponseEntity<Diary> getNextDiary(@PathVariable Long currentId) {
        Diary currentDiary = diaryService.getDiaryById(currentId);
        if (currentDiary == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Diary> publishedDiaries = diaryService.getPublishedDiaries();
        publishedDiaries.remove(currentDiary);
        if (publishedDiaries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Random random = new Random();
        Diary nextDiary = publishedDiaries.get(random.nextInt(publishedDiaries.size()));
        return new ResponseEntity<>(nextDiary, HttpStatus.OK);
    }

    // 点赞日记
    @PostMapping("/{diaryId}/like")
    public ResponseEntity<Void> likeDiary(@PathVariable Long diaryId) {
        diaryService.likeDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 添加评论
    @PostMapping("/{diaryId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long diaryId, @RequestBody Comment comment) {
        diaryService.addComment(diaryId, comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // 获取评论列表
    @GetMapping("/{diaryId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByDiaryId(@PathVariable Long diaryId) {
        List<Comment> comments = diaryService.getCommentsByDiaryId(diaryId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}