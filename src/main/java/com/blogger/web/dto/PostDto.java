package com.blogger.web.dto;

import com.blogger.data.models.Author;
import com.blogger.data.models.Comment;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostDto {
    private String title;

    private String content;

    private MultipartFile imageFile;
}
