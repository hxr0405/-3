package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    @Query("SELECT m FROM Music m WHERE m.title LIKE %:keyword% OR m.artist LIKE %:keyword% OR m.album LIKE %:keyword%")
    List<Music> searchMusicUseKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM Music m JOIN m.categories c WHERE c.name = :categoryName")
    List<Music> searchMusicUseCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT m FROM Music m WHERE m.title = :title")
    Optional<Music> findByTitle(@Param("title") String title);

    @Query("SELECT m FROM Music m WHERE m.id = :musicId")
    Music getMusicById(@Param("musicId") Long musicId);

    @Query("SELECT m FROM Music m")
    List<Music> getAllMusic();
}
