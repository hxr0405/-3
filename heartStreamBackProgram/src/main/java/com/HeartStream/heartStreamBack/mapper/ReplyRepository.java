package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPostId(Long postId);
}
