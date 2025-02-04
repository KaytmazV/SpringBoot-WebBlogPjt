package org.example.blogagain.controller;


import jakarta.validation.Valid;
import org.example.blogagain.entity.PostEntity;
import org.example.blogagain.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/getallpost")
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        PostEntity post = postService.getPostById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostEntity> createPost(@Valid @RequestBody PostEntity post) {
        PostEntity postEntity = postService.createPost(post);
        return new ResponseEntity<>(postEntity, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<Optional<PostEntity>> updatePost(@RequestBody PostEntity post) {
        Optional<PostEntity> postEntity = postService.updatePost(post);
        if (postEntity.isPresent()) {
            return new ResponseEntity<>(postEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
