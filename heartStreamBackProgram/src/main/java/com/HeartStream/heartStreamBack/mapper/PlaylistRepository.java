package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.Playlist;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlaylistRepository {
    @Select("SELECT * FROM playlist WHERE name = #{name} AND user_id = #{userId}")
    Integer countByNameAndUserId(String name, Long userId);

    @Select("SELECT * FROM playlist WHERE name = #{playlistName}")
    Playlist getPlaylist(String playlistName);

    @Insert("INSERT INTO playlist (name, user_id) VALUES (#{name}, #{userId})")
    Integer createPlaylist(String name, Long userId);

    @Delete("DELETE FROM playlist WHERE name = #{name} AND user_id = #{userId}")
    Integer deletePlaylist(String name, Long userId);

    @Select("SELECT * FROM playlist WHERE user_id = #{userId}")
    List<Playlist> getUserPlaylists(Long userId);

    @Select("SELECT * FROM playlist WHERE name = #{playlistName} and ispublished = 1")
    List<Playlist> getPlaylistByName(String playlistName);
}
