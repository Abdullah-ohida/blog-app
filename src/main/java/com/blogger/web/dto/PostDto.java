package com.blogger.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostDto {
    @NotEmpty(message="Title cannot be empty")
    private String title;

    @NotEmpty(message = "Content cannot be empty")
    private String content;

    private MultipartFile imageFile;
}
