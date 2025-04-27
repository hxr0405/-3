package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.MusicPlayer;
import com.HeartStream.heartStreamBack.model.PlayMode;
import com.HeartStream.heartStreamBack.model.Playlist;
import com.HeartStream.heartStreamBack.service.MusicService;
import com.HeartStream.heartStreamBack.service.PlaylistService;
import com.HeartStream.heartStreamBack.utils.OSSUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private MusicPlayer musicPlayer;

    @Autowired
    private OSSUtil ossUtil;

    // 搜索音乐
    @PostMapping("/search/keyword")
    public List<Music> searchMusicByKeyword(@RequestParam String keyword, @PathVariable Long userId) {
        log.info("Searching music by keyword: {}", keyword);
        musicPlayer.setUserId(userId);
        return musicService.searchMusicByKeyword(keyword);
    }

    //搜索音乐（类型）
    @PostMapping("/search/category")
    public List<Music> searchMusicByCategoryName(@RequestParam String categoryName, @PathVariable Long userId) {
        log.info("Searching music by category name: {}", categoryName);
        musicPlayer.setUserId(userId);
        return musicService.searchMusicByCategoryName(categoryName);
    }

    // 播放音乐
    @GetMapping("/playMusic")
    public ResponseEntity<InputStreamResource> getMusicById(@RequestParam Long id, @PathVariable Long userId) {
        Music music = musicService.getMusicById(id);
        if (music == null) {
            return ResponseEntity.notFound().build();
        }

        String filePath = music.getFilePath();// 构建 OSS 对象名称
        OSS ossClient =ossUtil.getOSSClient();
        String bucketName = "heart-stream"; // 替换为你的 OSS bucket 名称

        OSSObject ossObject = ossClient.getObject(bucketName, filePath);
        InputStream inputStream = ossObject.getObjectContent();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(new InputStreamResource(inputStream));
    }

    // 获取封面
    @GetMapping("/cover")
    public ResponseEntity<Resource> getCover(@RequestParam Long id, @PathVariable Long userId) {
        Music music = musicService.getMusicById(id);
        if (music == null) {
            return ResponseEntity.notFound().build();
        }

        String image = music.getImage();// 构建 OSS 对象名称
        OSS ossClient =ossUtil.getOSSClient();
        String bucketName = "heart-stream"; // 替换为你的 OSS bucket 名称

        OSSObject ossObject = ossClient.getObject(bucketName, image);
        InputStream inputStream = ossObject.getObjectContent();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(new InputStreamResource(inputStream));
    }

    // 获取歌手
    @GetMapping("/artist")
    public String getArtist(@RequestParam Long id, @PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        Music music = musicService.getMusicById(id);
        log.info("Music artist: {}", music.getArtist());
        return music.getArtist();
    }

    // 设置播放模式
    @PostMapping("/playMode")
    public void setPlayMode(@RequestParam PlayMode playMode, @PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        log.info("Setting play mode to: {}", playMode);
        musicPlayer.setPlayMode(playMode);
    }

    // 设置播放列表
    @PostMapping("/playList")
    public void setPlayList(@RequestBody Playlist playlist, @PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        log.info("Setting play list to: {}", playlist);
        try {
            List<Music> musicList = playlistService.getPlaylistMusics(playlist);
            List<Long> musicIdsList = musicList.stream()
                   .map(Music::getId)
                   .toList();
            log.info("Music Ids: {}", musicIdsList);
            musicPlayer.setPlayList(musicIdsList);
        }catch (Exception e){
            log.error("Error fetching music list: {}", e.getMessage());
        }
    }

    // 设置当前播放索引
    @PostMapping("/currentIndex")
    public void setCurrentIndex(@RequestParam Integer currentIndex, @PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        log.info("Setting current index to: {}", currentIndex);
        musicPlayer.setCurrentIndex(currentIndex);
    }

    // 播放下一首
    @GetMapping("/playNext")
    public String playNext(@PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        log.info("Playing next music");
        musicPlayer.playNext();
        return musicService.getMusicById(musicPlayer.getCurrentMusic()).getTitle();
    }

    // 播放上一首
    @GetMapping("/playPrevious")
    public String playPrevious(@PathVariable Long userId) {
        musicPlayer.setUserId(userId);
        log.info("Playing previous music");
        musicPlayer.playPrevious();
        return musicService.getMusicById(musicPlayer.getCurrentMusic()).getTitle();
    }
}


