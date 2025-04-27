package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.MusicRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MusicRecordMapper {
    @Insert("Insert into music_record (user_id, music_id) values (#{userId}, #{musicId})")
    void insertMusicRecord(MusicRecord musicRecord);

    @Select("select * from music_record where user_id = #{userId}")
    List<MusicRecord> getMusicRecordByUserId(Long userId);
}
