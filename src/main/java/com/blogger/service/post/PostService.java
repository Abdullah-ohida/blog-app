package com.blogger.service.post;

import com.blogger.data.models.Comment;
import com.blogger.data.models.Post;
import com.blogger.web.dto.PostDto;
import com.blogger.web.exceptions.PostDoestNotExistsException;
import com.blogger.web.exceptions.PostObjectIsNullException;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto) throws PostObjectIsNullException;
    List<Post> findAllPosts();
    Post updatePost(PostDto postDto);
    Post findPostById(Long id) throws PostDoestNotExistsException;
    void deletePostById(Long id);
    Post addCommentToPost(Long id, Comment comment);
    List<Post> findPostInDescOrder();

}
