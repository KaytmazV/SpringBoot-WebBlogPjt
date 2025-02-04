package org.example.blogagain.controller;


import jakarta.validation.Valid;
import org.example.blogagain.entity.CommentEntity;
import org.example.blogagain.services.CommentService;
import org.example.blogagain.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("/getallcomment")
    public ResponseEntity<List<CommentEntity>> getAllComments() {
        List<CommentEntity> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }

    @GetMapping("/getCommentById/{id}")
    public ResponseEntity<CommentEntity> getCommentById(@PathVariable Long id) {
        CommentEntity commentEntity = commentService.getCommentByID(id);
        if (commentEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commentEntity, HttpStatus.OK);
        }
    }

    @PostMapping("/createComment")
    public ResponseEntity<CommentEntity> createComment(@Valid @RequestBody CommentEntity commentEntity) {
        try {
            CommentEntity commentEntityCreated = commentService.createComment(commentEntity);
            return new ResponseEntity<>(commentEntityCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Hatalı durum için 400 kodu
        }
    }

    @PutMapping("/updateComment")
    public ResponseEntity<Optional<CommentEntity>> updateComment(@RequestBody CommentEntity commentEntity) {
        Optional<CommentEntity> commentEntityUpdated = commentService.updateComment(commentEntity);
        if (commentEntityUpdated.isPresent()) {
            return new ResponseEntity<>(commentEntityUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
