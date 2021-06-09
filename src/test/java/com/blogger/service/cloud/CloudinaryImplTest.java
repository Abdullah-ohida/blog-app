package com.blogger.service.cloud;

import com.blogger.web.dto.PostDto;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Slf4j
class CloudinaryImplTest {

    @Autowired @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImageMultiPartFile(){
//        Define a file
        File file = new File("/home/whalewalker/Whalewalker/Personal/blogApp/src/main/resources/static/asset/images/author-image1.jpg");
        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();

        params.put("public_id", "blogapp/post_file1");
        params.put("overwrite", true);

        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        } catch (IOException e) {
            log.info("Error occurred --> {}", e.getMessage());
        }

    }

    @Test
    @DisplayName("Upload multiple image test")
    void uploadImageTest() throws IOException {
        PostDto postDto = new PostDto();
        postDto.setTitle("Test");
        postDto.setContent("Test");

        Path path = Paths.get("/home/whalewalker/Whalewalker/Personal/blogApp/src/main/resources/static/asset/images/author-image1.jpg");
        MultipartFile multipartFile = new MockMultipartFile("author-image1.jpg", "author-image1.jpg", "img/jpg", Files.readAllBytes(path));

        log.info("Multipart Object created --> {}", multipartFile);
        assertThat(multipartFile).isNotNull();
        postDto.setImageFile(multipartFile);

        log.info("File name --> {}", postDto.getImageFile().getOriginalFilename());
        cloudStorageServiceImpl.uploadImage(multipartFile, ObjectUtils.asMap(
                "public_id", "blogapp" + extractFileName(Objects.requireNonNull(postDto.getImageFile().getOriginalFilename()))));

        assertThat(postDto.getImageFile().getOriginalFilename()).isEqualTo("author-image1.jpg");
    }

    private String extractFileName(String filename){
        return filename.split("\\.")[0];
    }
}