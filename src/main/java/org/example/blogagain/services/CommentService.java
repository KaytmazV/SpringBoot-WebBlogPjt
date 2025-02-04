package org.example.blogagain.services;

import org.example.blogagain.entity.CommentEntity;
import org.example.blogagain.entity.PostEntity;
import org.example.blogagain.repository.CommentRepository;
import org.example.blogagain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }

    public CommentEntity getCommentByID(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment with ID " + id + " not found"));
    }

    public CommentEntity createComment(CommentEntity commentEntity) {
        if (commentEntity == null || commentEntity.getText() == null || commentEntity.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
        if (commentEntity.getPost() == null || commentEntity.getPost().getPostID() == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        PostEntity post = postRepository.findById(commentEntity.getPost().getPostID())
                .orElseThrow(() -> new IllegalArgumentException("Post with ID " + commentEntity.getPost().getPostID() + " not found"));

        commentEntity.setPost(post);
        return commentRepository.save(commentEntity);
    }

    public Optional<CommentEntity> updateComment(CommentEntity commentEntity) {
        Optional<CommentEntity> commentUpdate = commentRepository.findById(commentEntity.getCommentID());
        if (commentUpdate.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(commentRepository.save(commentEntity));
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("Comment with ID " + id + " not found");
        }
        commentRepository.deleteById(id);
    }
}
