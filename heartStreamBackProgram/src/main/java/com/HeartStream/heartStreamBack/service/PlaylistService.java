package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.Music;
import com.HeartStream.heartStreamBack.model.Playlist;

import java.util.List;

public interface PlaylistService {
    boolean createPlaylist(String name, Long userId);

    boolean deletePlaylist(String name, Long userId);

    boolean addMusicToPlaylist(String playlistName, Music music);

    boolean deleteMusicFromPlaylist(String playlistName, Music music);

    List<Playlist> getUserPlaylists(Long userId);

    List<Music> getPlaylistMusics(Playlist playlist);

    List<Playlist> getPlaylist(String playlistName);
}
