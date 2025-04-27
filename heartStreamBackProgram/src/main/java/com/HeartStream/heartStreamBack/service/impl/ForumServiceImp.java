package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.PostRepository;
import com.HeartStream.heartStreamBack.mapper.ReplyRepository;
import com.HeartStream.heartStreamBack.mapper.UserMapper;
import com.HeartStream.heartStreamBack.model.Post;
import com.HeartStream.heartStreamBack.model.Reply;
import com.HeartStream.heartStreamBack.model.User;
import com.HeartStream.heartStreamBack.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ForumServiceImp implements ForumService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingOrContentContaining(keyword);
    }

    @Override
    public Post createPost(Post post, Long userId) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Reply createReply(Reply reply, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userMapper.getUserById(userId);
        if (post == null || user == null) {
            throw new RuntimeException("Post or user not found");
        }
        reply.setPost(post);
        reply.setUser(user);
        return replyRepository.save(reply);
    }

    @Override
    public List<Reply> getRepliesByPostId(Long postId) {
        return replyRepository.findByPostId(postId);
    }
}
