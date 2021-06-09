package com.blogger.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "blog_post")
@Data

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(length = 500)
    private String coverImageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn
    private Author author;

    @CreationTimestamp
    private LocalDateTime datePublished;

    @UpdateTimestamp
    private LocalDateTime dateModified;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    public void addComment(Comment... comment) {
        if (this.comments == null)
            this.comments = new ArrayList<>();
        this.comments.addAll(Arrays.asList(comment));
    }
}
