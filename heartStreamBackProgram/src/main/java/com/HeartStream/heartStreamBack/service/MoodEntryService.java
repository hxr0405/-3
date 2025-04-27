package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.Mood;
import com.HeartStream.heartStreamBack.model.MoodEntry;

import java.util.List;

public interface MoodEntryService {
    void createMoodEntry(Long userId, Mood mood);

    List<MoodEntry> getMoodEntry(Long userId);
}
