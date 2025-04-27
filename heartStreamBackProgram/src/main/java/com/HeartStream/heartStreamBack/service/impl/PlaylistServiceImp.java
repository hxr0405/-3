package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.MusicRepository;
import com.HeartStream.heartStreamBack.mapper.PlaylistMusicRepository;
import com.HeartStream.heartStreamBack.mapper.PlaylistRepository;
import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.Playlist;
import com.HeartStream.heartStreamBack.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMusicRepository playlistMusicRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public boolean createPlaylist(String name, Long userId) {
        Integer count = playlistRepository.countByNameAndUserId(name, userId);
        if (count == null) {
            count = 0;
        }
        if (count > 0) {
            log.info("歌单已存在");
            return false;
        }
        try {
            int result = playlistRepository.createPlaylist(name, userId);
            if (result > 0) {
                log.info("创建成功");
                return true;
            }else {
                log.info("创建失败");
                return false;
            }
        }catch (Exception e){
            log.error("创建异常", e);
            return false;
        }
    }

    @Override
    public boolean deletePlaylist(String name, Long userId) {
        try {
            int result = playlistRepository.deletePlaylist(name, userId);
            if (result > 0) {
                log.info("删除成功");
                return true;
            }else {
                log.info("删除失败");
                return false;
            }
        }catch (Exception e){
            log.error("删除异常", e);
            return false;
        }
    }

    @Override
    public boolean addMusicToPlaylist(String playlistName, Music music) {
        Playlist playlist = playlistRepository.getPlaylist(playlistName);
        if (playlist == null) {
            log.info("歌单不存在");
            return false;
        }

        Integer count = playlistMusicRepository.findByPlaylistIdAndMusicId(playlist.getId(), music.getId());
        if (count == null) {
            count = 0;
        }
        if (count > 0) {
            log.info("歌曲已经存在于歌单中");
            return false;
        }

        try {
            int result = playlistMusicRepository.addMusicToPlaylist(playlist.getId(), music.getId());
            if (result > 0) {
                log.info("添加成功");
                return true;
            }else {
                log.info("添加失败");
                return false;
            }
        }catch (Exception e){
            log.error("添加异常", e);
            return false;
        }
    }

    @Override
    public boolean deleteMusicFromPlaylist(String playlistName, Music music) {
        Playlist playlist = playlistRepository.getPlaylist(playlistName);
        if (playlist == null) {
            return false;
        }
        try {
            int result = playlistMusicRepository.deleteMusicFromPlaylist(playlist.getId(), music.getId());
            if (result > 0) {
                log.info("删除成功");
                return true;
            }else {
                log.info("删除失败");
                return false;
            }
        }catch (Exception e){
            log.error("删除异常", e);
            return false;
        }
    }

    @Override
    public List<Playlist> getUserPlaylists(Long userId) {
        try {
            log.info("获取用户歌单：{}", userId);
            return playlistRepository.getUserPlaylists(userId);
        }catch (Exception e){
            log.error("获取异常", e);
            return List.of();
        }
    }

    @Override
    public List<Music> getPlaylistMusics(Playlist playlist) {
        try {
            log.info("获取歌单歌曲：{}", playlist.getName());
            List<Long> musicIds = playlistMusicRepository.findMusicIds(playlist.getId());
            List<Music> musics = new ArrayList<>();
            for (Long musicId : musicIds) {
                Music music = musicRepository.getMusicById(musicId);
                if (music != null) {
                    musics.add(music);
                }
            }
            return musics;
        }catch (Exception e){
            log.error("获取异常", e);
            return List.of();
        }
    }

    @Override
    public List<Playlist> getPlaylist(String playlistName) {
        try {
            log.info("获取歌单：{}", playlistName);
            return playlistRepository.getPlaylistByName(playlistName);
        }catch (Exception e){
            log.error("获取异常", e);
            return List.of();
        }

    }
}
