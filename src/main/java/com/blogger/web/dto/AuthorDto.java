package com.blogger.web.dto;

import com.blogger.data.models.Post;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
public class AuthorDto {
    private String firstName;
    private String lastName;
    private String profession;
    private String userName;
    private String email;
    private String phoneNumber;

}
