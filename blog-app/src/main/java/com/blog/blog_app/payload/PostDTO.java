package com.blog.blog_app.payload;

import com.blog.blog_app.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private String postTitle;

    private  String content;

    private String image;

    private Date date;

    //Only defining User and Category only creates error of infinite iteration
    private CategoryDTO category;
    private UserDTO user;
}
