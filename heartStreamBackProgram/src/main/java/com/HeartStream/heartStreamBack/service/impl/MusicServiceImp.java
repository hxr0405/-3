package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.MusicRecordMapper;
import com.HeartStream.heartStreamBack.mapper.MusicRepository;
import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.MusicRecord;
import com.HeartStream.heartStreamBack.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MusicServiceImp implements MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private MusicRecordMapper musicRecordMapper;
    @Override
    public List<Music> searchMusicByKeyword(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return musicRepository.searchMusicUseKeyword(keyword);
        }
        return List.of();
    }

    @Override
    public List<Music> searchMusicByCategoryName(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            return musicRepository.searchMusicUseCategoryName(categoryName);
        }
        return List.of();
    }

    @Override
    public Music getMusicByTitle(String title) {
        Optional<Music> musicOptional = musicRepository.findByTitle(title);
        return musicOptional.orElse(null);
    }

    @Override
    public Music getMusicById(Long id) {
        Optional<Music> musicOptional = musicRepository.findById(id);
        return musicOptional.orElse(null);
    }

    @Override
    public List<Long> getAllMusic() {
        try {
            List<Music> musicList = musicRepository.getAllMusic();
            return musicList.stream()
                    .map(Music::getId)
                    .toList();
        }catch (Exception e){
            log.error("Error fetching all music titles: {}", e.getMessage());
            return List.of();
        }
    }

    @Override
    public void createMusicRecord(Long userId, Long musicId) {
        MusicRecord musicRecord = new MusicRecord();
        musicRecord.setUserId(userId);
        musicRecord.setMusicId(musicId);
        musicRecordMapper.insertMusicRecord(musicRecord);
    }

    @Override
    public List<MusicRecord> getAllMusicRecords(Long userId) {
        return musicRecordMapper.getMusicRecordByUserId(userId);
    }

    @Override
    public MusicRecord getLastMusicRecord(Long userId) {
        List<MusicRecord> allMusicRecords = musicRecordMapper.getMusicRecordByUserId(userId);
        if (allMusicRecords!= null &&!allMusicRecords.isEmpty()) {
           return allMusicRecords.get(allMusicRecords.size() - 1);
        }
        return null;
    }
}
