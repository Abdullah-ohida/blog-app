package com.blogger.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {
    private String commentatorName;
    private String content;
}
