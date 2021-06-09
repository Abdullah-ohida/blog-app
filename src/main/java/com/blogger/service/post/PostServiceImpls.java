package com.blogger.service.post;

import com.blogger.data.models.Comment;
import com.blogger.data.models.Post;
import com.blogger.data.repository.PostRepository;
import com.blogger.service.cloud.CloudStorageService;
import com.blogger.web.dto.PostDto;
import com.blogger.web.exceptions.PostDoestNotExistsException;
import com.blogger.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpls implements PostService{
    @Autowired
    PostRepository postRepository;

    @Autowired
    CloudStorageService cloudStorageService;


    @Override
    public Post createPost(PostDto postDto) throws PostObjectIsNullException {
        if(postDto == null){
            throw new PostObjectIsNullException("Post cannot be null");
        }

        Post post = new Post();

        if(postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()){
            try {
                Map<?, ?> uploadResult = cloudStorageService.uploadImage(postDto.getImageFile(), ObjectUtils.asMap("public_id"
                ,"blogapp/" + extractFileName( postDto.getImageFile().getName())));

                post.setCoverImageUrl(String.valueOf(uploadResult.get("url")));
                log.info("Image url --> {}", uploadResult.get("url"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        post.setTitle(postDto.getTitle());
        post.setContent((postDto.getContent()));

        try {
            return postRepository.save(post);
        }catch (DataIntegrityViolationException err){
            log.info("Exception occurred ==> {}", err.getMessage());
            throw err;
        }
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findPostById(Long id) throws PostDoestNotExistsException {
        if(id == null){
            throw new NullPointerException("Id cannot be null");
        }

        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()){
            return post.get();
        }else {
            throw new PostDoestNotExistsException("post with is --> {}");
        }
    }

    @Override
    public void deletePostById(Long id) {

    }

    @Override
    public Post addCommentToPost(Long id, Comment comment) {
        return null;
    }

    @Override
    public List<Post> findPostInDescOrder() {
        return postRepository.findByOrderByDatePublishedDesc();
    }

    private String extractFileName(String filename){
        return filename.split("\\.")[0];
    }
}
