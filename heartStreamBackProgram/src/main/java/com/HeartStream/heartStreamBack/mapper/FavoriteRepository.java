package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
