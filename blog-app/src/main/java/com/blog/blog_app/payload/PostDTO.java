package com.blog.blog_app.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Integer postId;

    @NotNull
    private String postTitle;

    @NotNull
    private  String content;

    private String image;

    private Date date;

    //Only defining User and Category only creates error of infinite iteration
    private CategoryDTO category;
    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();
}
