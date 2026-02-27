package com.blog.blog_app.service;

import com.blog.blog_app.entity.Post;
import com.blog.blog_app.payload.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    //Create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    //Update
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    //Delete
    void deletePost(Integer postId);

    //get all posts
    List<PostDTO> getAllPost();

    //Get Single post
    PostDTO getPostById(Integer postId);

    //Get all posts by user
    List<PostDTO> getAllPostByUser(Integer userId);

    //Get all posts by category
    List<PostDTO> getAllPostByCategory(Integer categoryId);

    //Search
    List<PostDTO> searchAllPost(String keyword);

}
