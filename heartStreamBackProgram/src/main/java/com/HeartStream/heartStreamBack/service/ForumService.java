package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.model.Post;
import com.HeartStream.heartStreamBack.model.Reply;

import java.util.List;

public interface ForumService {
    List<Post> getAllPosts();

    List<Post> searchPosts(String keyword);

    Post createPost(Post post, Long userId);

    Reply createReply(Reply reply, Long postId, Long userId);

    List<Reply> getRepliesByPostId(Long postId);
}
