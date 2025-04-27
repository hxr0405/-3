package com.HeartStream.heartStreamBack.model;

import com.HeartStream.heartStreamBack.service.MusicService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MusicPlayer {
    @Autowired
    private MusicService musicService; // 音乐服务

    private Long userId;// 用户ID
    private List<Long> playList; // 歌单列表
    private int currentIndex = 0; // 当前播放歌曲索引
    private PlayMode playMode = PlayMode.ORDER_PLAY; // 播放模式
    private Random random = new Random(); // 随机数生成器
    private List<MusicRecord> playRecords = new ArrayList<>();

    @Autowired
    public MusicPlayer(MusicService musicService) {
        this.musicService = musicService;
        initializePlayList();
    }

    private void initializePlayList() {
        playList = musicService.getAllMusic();
    }

    public void playNext() {
        switch (playMode) {
            case SINGLE_PLAY:
                // 单曲循环，不改变当前索引
                break;
            case RANDOM_PLAY:
                // 随机播放
                currentIndex = random.nextInt(playList.size());
                break;
            case ORDER_PLAY:
                // 顺序播放
                currentIndex = (currentIndex + 1) % playList.size();
                break;
            case EXPLORE_PLAY:
                // 探索播放，从所有音乐中随机选择一首
                List<Long> allMusic = musicService.getAllMusic();
                currentIndex = random.nextInt(allMusic.size());
                playList = allMusic;
                break;
        }
        MusicRecord musicRecord = new MusicRecord();
        Long musicId = musicService.getMusicById(playList.get(currentIndex)).getId();
        musicRecord.setUserId(userId);
        musicRecord.setMusicId(musicId);
        playRecords.add(musicRecord);
    }

    public void playPrevious() {
        if (playRecords != null && !playRecords.isEmpty() && currentIndex > 0) {
            currentIndex--;
        }else {
            if (playRecords != null && !playRecords.isEmpty()) {
                currentIndex = (currentIndex - 1 + playRecords.size()) % playRecords.size();
            }
        }
    }

    public Long getCurrentMusic() {
        if (playList != null && !playList.isEmpty()) {
            return playList.get(currentIndex);
        }
        return null;
    }
}
