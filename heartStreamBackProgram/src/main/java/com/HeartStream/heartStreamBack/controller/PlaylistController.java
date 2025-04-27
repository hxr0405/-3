package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.Playlist;
import com.HeartStream.heartStreamBack.model.Result;
import com.HeartStream.heartStreamBack.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/create")
    public Result createPlaylist(@RequestParam String name, @PathVariable Long userId) {
        log.info("创建歌单：{}", name);
        boolean flag = playlistService.createPlaylist(name, userId);
        if (flag) {
            return Result.success();
        }else {
            return Result.error("创建歌单失败");
        }
    }

    @PostMapping("/delete")
    public Result deletePlaylist(@RequestParam String name, @PathVariable Long userId) {
        log.info("删除歌单：{}{}", name, userId);
        boolean flag = playlistService.deletePlaylist(name, userId);
        if (flag) {
            return Result.success();
        }else {
            return Result.error("删除歌单失败");
        }
    }

    @PostMapping("/addMusic")
    public Result addMusicToPlaylist(@RequestParam String playlistName, @RequestBody Music music) {
        log.info("添加音乐到歌单：{}{}", playlistName, music);
        boolean flag = playlistService.addMusicToPlaylist(playlistName, music);
        if (flag) {
            return Result.success();
        }else {
            return Result.error("添加音乐到歌单失败");
        }
    }

    @PostMapping("/deleteMusic")
    public Result deleteMusicFromPlaylist(@RequestParam String playlistName, @RequestBody Music music) {
        log.info("删除音乐从歌单：{}{}", playlistName, music);
        boolean flag = playlistService.deleteMusicFromPlaylist(playlistName, music);
        if (flag) {
            return Result.success();
        }else {
            return Result.error("删除音乐从歌单失败");
        }
    }

    @GetMapping("/userPlaylists")
    public List<Playlist> getUserPlaylists(@PathVariable Long userId) {
        log.info("获取用户歌单：{}", userId);
        return playlistService.getUserPlaylists(userId);
    }

    @GetMapping("/playlistMusics")
    public List<Music> getPlaylistMusics(@RequestBody Playlist playlist) {
        log.info("获取歌单音乐：{}", playlist);
        return playlistService.getPlaylistMusics(playlist);
    }

    @GetMapping("/playlist")
    public List<Playlist> getPlaylist(@RequestParam String playlistName) {
        log.info("获取歌单：{}", playlistName);
        return playlistService.getPlaylist(playlistName);
    }
}
