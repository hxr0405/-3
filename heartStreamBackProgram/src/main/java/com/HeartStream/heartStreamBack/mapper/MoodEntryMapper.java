// MoodEntryMapper.java
package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.MoodEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MoodEntryMapper {
    @Insert("insert into mood_entry (user_id, mood) values (#{userId}, #{mood})")
    void insertMoodEntry(Long userId, String mood);

    @Select("select * from mood_entry where user_id = #{userId} order by date asc")
    List<MoodEntry> getMoodEntry(Long userId);
}

