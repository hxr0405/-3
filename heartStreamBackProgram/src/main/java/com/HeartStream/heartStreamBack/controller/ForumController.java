package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.model.Post;
import com.HeartStream.heartStreamBack.model.Reply;
import com.HeartStream.heartStreamBack.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userId}/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = forumService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> posts = forumService.searchPosts(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long userId) {
        Post newPost = forumService.createPost(post, userId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PostMapping("/posts/{postId}/replies")
    public ResponseEntity<Reply> createReply(@RequestBody Reply reply, @PathVariable Long postId, @PathVariable Long userId) {
        Reply newReply = forumService.createReply(reply, postId, userId);
        return new ResponseEntity<>(newReply, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/replies")
    public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable Long postId) {
        List<Reply> replies = forumService.getRepliesByPostId(postId);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }
}
