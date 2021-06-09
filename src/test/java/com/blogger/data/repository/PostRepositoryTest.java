package com.blogger.data.repository;

import com.blogger.data.models.Author;
import com.blogger.data.models.Comment;
import com.blogger.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
@Sql(scripts = "classpath:db/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    Post blogPost;
    Author author;
    Comment comment;

    @BeforeEach
    void setUp() {
        blogPost = new Post();
        author = new Author();
        comment = new Comment("Adex", "Cool");
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Financial technology is the technology " +
                "and innovation that aims to compete with traditional financial methods" +
                " in the delivery of financial services. It is an" +
                " emerging industry that uses technology to improve activities in finance.");

        author.setFirstName("Abdul");
        author.setUserName("Whalewalker");
        author.setLastName("Ismail");
        author.setEmail("adex@gmail.com");
        author.setPhoneNumber("09087654433");

    }

    @AfterEach
    void tearDown() {
        blogPost = null;
        author = null;
    }

    @Test
    void savePostToDatabaseTest() {
        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);

        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingPostWithExistingTitle() {

        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);

        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Financial technology is the technology " +
                "and innovation that aims to compete with traditional financial methods" +
                " in the delivery of financial services. It is an" +
                " emerging industry that uses technology to improve activities in finance.");

        log.info("Created a blog post --> {}", blogPost2);


        assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(blogPost2));
    }


    @Test
    @DisplayName("When post is create then author is created")
    void createAuthor() {
        log.info("Created a blog post --> {}", blogPost);
//        Map relationship
        blogPost.setAuthor(author);
        author.addPost(blogPost);
        postRepository.save(blogPost);
        Post savedPost = postRepository.findPostByTitle("What is Fintech?");
        assertThat(savedPost.getTitle()).isNotNull();
        log.info("Blog post after saving to Db--> {}", blogPost);
    }

    @Test
    void findAllPostInDatabaseTest() {
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }

    @Test
    void findAllAuthorPostByUserNameTest() {
        createAuthor();
        List<Post> authorPosts = postRepository.findPostByAuthorUserName(author.getUserName());
        assertThat(authorPosts).isNotNull();
        assertThat(authorPosts).hasSize(1);
    }


    @Test

    void deleteAPostTest() {
        Post savedPost = postRepository.findById(41L).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("post fetch from the database --> {}", savedPost);
        postRepository.deleteById(savedPost.getId());
        Post deletedPost = postRepository.findById(41L).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    void getPostByIdTest() {
        Post getPost = postRepository.findById(42L).orElse(null);
        assertThat(getPost).isNotNull();
        log.info("get post from the database --> {}", getPost);
    }

    @Test
    void updateSavedPostTest() {
        Post postToUpdate = postRepository.findById(41L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getTitle()).isEqualTo("Title post 1");
        log.info("post fetch from the database --> {}", postToUpdate);

        postToUpdate.setTitle("I love you");
        postToUpdate.setContent("New Life I live");
        postRepository.save(postToUpdate);


        Post updatedPost = postRepository.findById(postToUpdate.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("I love you");
        log.info("updated fetch from the database --> {}", updatedPost);
    }

    @Test
    void updatePostAuthorTest() {
        Post postToUpdate = postRepository.findById(41L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getAuthor()).isNull();
        log.info("post fetch from the database --> {}", postToUpdate);

        postToUpdate.setAuthor(author);
        postRepository.save(postToUpdate);


        Post updatedPost = postRepository.findById(postToUpdate.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getLastName()).isEqualTo("Ismail");

        log.info("Updated Post --> {}", updatedPost);
    }

    @Test
    void updatePostCommentTest(){
        Post postToUpdate = postRepository.findById(41L).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getAuthor()).isNull();
        log.info("post fetch from the database --> {}", postToUpdate);

        postToUpdate.addComment(comment);
        postRepository.save(postToUpdate);

        Post updatedPost = postRepository.findById(postToUpdate.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getComments()).isNotNull();
        assertThat(updatedPost.getComments()).hasSize(1);
        assertThat(updatedPost.getComments().get(0).getCommentatorName()).isEqualTo("Adex");

        log.info("Updated Post --> {}", updatedPost);
    }

    @Test
    @DisplayName("Find all post in descending order")
    void getAllPostInDesc(){
       List<Post> allPosts = postRepository.findByOrderByDatePublishedDesc();
       assertTrue(allPosts.get(0).getDatePublished().isAfter(allPosts.get(1).getDatePublished()));

       allPosts.forEach(post -> {
           log.info("Post Data --> {}", post.getDatePublished());
       });
    }
}