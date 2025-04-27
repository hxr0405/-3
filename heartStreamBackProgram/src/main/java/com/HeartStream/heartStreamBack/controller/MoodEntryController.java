package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Mood;
import com.HeartStream.heartStreamBack.model.MoodEntry;
import com.HeartStream.heartStreamBack.service.MoodEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/mood-entries")
public class MoodEntryController {
    @Autowired
    private MoodEntryService moodEntryService;

    @PostMapping("/create")
    public void createMoodEntry(@PathVariable Long userId, @RequestParam Mood mood) {
        moodEntryService.createMoodEntry(userId, mood);
    }

    @GetMapping("/get")
    public List<MoodEntry> getMoodEntry(@PathVariable Long userId) {
        return moodEntryService.getMoodEntry(userId);
    }
}
