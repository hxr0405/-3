package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.MusicRecord;

import java.util.List;

public interface MusicService {
    List<Music> searchMusicByKeyword(String keyword);
    List<Music> searchMusicByCategoryName(String categoryName);
    Music getMusicByTitle(String title);
    Music getMusicById(Long id);
    List<Long> getAllMusic();
    void createMusicRecord(Long userId, Long musicId);
    List<MusicRecord> getAllMusicRecords(Long userId);
    MusicRecord getLastMusicRecord(Long userId);
}
