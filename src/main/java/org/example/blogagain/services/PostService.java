package org.example.blogagain.services;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.blogagain.entity.PostEntity;
import org.example.blogagain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    public PostEntity getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with ID " + id + " not found"));
    }

    public PostEntity createPost(PostEntity postEntity) {
        if (postEntity == null || postEntity.getTitle() == null || postEntity.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Post title cannot be empty");
        }
        if (postEntity.getContent() == null || postEntity.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }
        return postRepository.save(postEntity);
    }

    public Optional<PostEntity> updatePost(PostEntity postEntity) {
        Optional<PostEntity> postUpdate = postRepository.findById(postEntity.getPostID());
        if (postUpdate.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(postRepository.save(postEntity));
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post with ID " + id + " not found");
        }
        postRepository.deleteById(id);
    }
}
