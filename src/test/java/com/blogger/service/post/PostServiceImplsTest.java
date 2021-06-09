package com.blogger.service.post;

import com.blogger.data.models.Post;
import com.blogger.data.repository.PostRepository;
import com.blogger.web.dto.PostDto;
import com.blogger.web.exceptions.PostObjectIsNullException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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
    void whenTheSaveMethodIsCalled_ThenRepositoryIsCalledOnce() throws PostObjectIsNullException {
        when(postServiceImpl.createPost(new PostDto())).thenReturn(testPost);
        postServiceImpl.createPost(new PostDto());

        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    @DisplayName("whenTheFindAllMethodIsCalled_thenReturnAListOfPost")
    void postTest(){
        List<Post> postList = new ArrayList<>();
        when(postServiceImpl.findAllPosts()).thenReturn(postList);

        postServiceImpl.findAllPosts();

        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("WhenTheFindByIdMethodIsCalled_thenReturnASpecificPostOfThatId")
    void getPostByIdTest(){

    }
}