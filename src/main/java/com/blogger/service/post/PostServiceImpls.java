package com.blogger.service.post;

import com.blogger.data.models.Comment;
import com.blogger.data.models.Post;
import com.blogger.data.repository.PostRepository;
import com.blogger.web.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PostServiceImpls implements PostService{
    @Autowired
    PostRepository postRepository;


    @Override
    public Post createPost(PostDto postDto) {
        Post post = new Post();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(postDto, post);

        log.info("Post object after mapping --> {}", post);

        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return null;
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findPostById(Long id) {
        return null;
    }

    @Override
    public void deletePostById(Long id) {

    }

    @Override
    public Post addCommentToPost(Long id, Comment comment) {
        return null;
    }
}
