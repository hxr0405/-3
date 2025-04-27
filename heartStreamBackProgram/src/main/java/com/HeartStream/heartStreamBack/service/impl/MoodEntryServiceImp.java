// MoodEntryServiceImp.java
package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.MoodEntryMapper;
import com.HeartStream.heartStreamBack.model.Mood;
import com.HeartStream.heartStreamBack.model.MoodEntry;
import com.HeartStream.heartStreamBack.service.MoodEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MoodEntryServiceImp implements MoodEntryService {
    @Autowired
    private MoodEntryMapper moodEntryMapper;

    @Override
    public void createMoodEntry(Long userId, Mood mood) {
        try {
            moodEntryMapper.insertMoodEntry(userId, mood.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MoodEntry> getMoodEntry(Long userId) {
        try {
            return moodEntryMapper.getMoodEntry(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

