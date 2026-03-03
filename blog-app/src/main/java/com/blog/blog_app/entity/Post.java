package com.blog.blog_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="post")
@NoArgsConstructor
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title")
    private String postTitle;

    private  String content;

    @Column(name = "image")
    private String imageName = "Default.png";

    private Date date;

    //Developing relations between different tables (In this case: User & Catgeory)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

}
