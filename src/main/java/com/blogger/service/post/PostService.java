package com.blogger.service.post;

import com.blogger.data.models.Comment;
import com.blogger.data.models.Post;
import com.blogger.web.dto.PostDto;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto);
    List<Post> findAllPosts();
    Post updatePost(PostDto postDto);
    Post findPostById(Long id);
    void deletePostById(Long id);
    Post addCommentToPost(Long id, Comment comment);

}
