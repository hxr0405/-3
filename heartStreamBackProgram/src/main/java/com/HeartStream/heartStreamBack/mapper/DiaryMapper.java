package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.Comment;
import com.HeartStream.heartStreamBack.model.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryMapper {
    @Insert("INSERT INTO Diary (user_id, title, content, is_published, create_time, update_time) " +
            "VALUES (#{userId}, #{title}, #{content}, #{isPublished}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertDiary(Diary diary);

    @Update("UPDATE Diary SET title = #{title}, content = #{content}, is_published = #{isPublished}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    void updateDiary(Diary diary);

    @Select("SELECT * FROM Diary WHERE id = #{id}")
    Diary getDiaryById(Long id);

    @Select("SELECT * FROM Diary WHERE is_published = TRUE")
    List<Diary> getPublishedDiaries();

    @Select("SELECT * FROM Diary WHERE id > #{currentId} AND is_published = TRUE ORDER BY id ASC LIMIT 1")
    Diary getNextDiary(Long currentId);

    @Update("UPDATE Diary SET like_count = like_count + 1 WHERE id = #{diaryId}")
    void incrementLikeCount(Long diaryId);

    @Insert("INSERT INTO Comment (diary_id, user_id, content) " +
            "VALUES (#{diaryId}, #{userId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComment(Comment comment);

    @Select("SELECT * FROM Comment WHERE diary_id = #{diaryId}")
    List<Comment> getCommentsByDiaryId(Long diaryId);
}
