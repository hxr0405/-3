package com.HeartStream.heartStreamBack.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlaylistMusicRepository {
    @Insert("INSERT INTO playlist_music (playlist_id, music_id) VALUES (#{playlistId}, #{musicId})")
    Integer addMusicToPlaylist(Long playlistId, Long musicId);

    @Delete("DELETE FROM playlist_music WHERE playlist_id = #{playlistId} AND music_id = #{musicId}")
    Integer deleteMusicFromPlaylist(Long playlistId, Long musicId);

    @Select("SELECT * FROM playlist_music WHERE playlist_id = #{playlistId} AND music_id = #{musicId}")
    Integer findByPlaylistIdAndMusicId(Long playlistId, Long musicId);

    @Select("SELECT music_id FROM playlist_music WHERE playlist_id = #{playlistId}")
    List<Long> findMusicIds(Long playlistId);
}
