package com.blogger.service.post;

import com.blogger.data.models.Post;
import com.blogger.data.repository.PostRepository;
import com.blogger.web.dto.PostDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PostServiceImplsTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    PostServiceImpls postServiceImpl;

    Post testPost;

    @BeforeEach
    void setUp() {
        testPost = new Post();
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenTheSaveMethodIsCalled_ThenRepositoryIsCalledOnce(){
        when(postServiceImpl.createPost(new PostDto())).thenReturn(testPost);
        postServiceImpl.createPost(new PostDto());

        verify(postRepository, times(1)).save(testPost);
    }
}