package org.example.blogagain.controller;

import jakarta.validation.Valid;
import org.example.blogagain.entity.PostEntity;
import org.example.blogagain.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/auth/posts")
public class AuthenticatedPostController {

    private final PostService postService;

    public AuthenticatedPostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getallpost")
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getPostById/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        PostEntity post = postService.getPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createPost")
    public ResponseEntity<PostEntity> createPost(@Valid @RequestBody PostEntity post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePost")
    public ResponseEntity<Optional<PostEntity>> updatePost(@RequestBody PostEntity post) {
        return ResponseEntity.ok(postService.updatePost(post));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}

