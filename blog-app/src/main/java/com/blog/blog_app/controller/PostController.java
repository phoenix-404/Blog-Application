package com.blog.blog_app.controller;

import com.blog.blog_app.entity.Post;
import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create New Post
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDTO newPost = this.postService.createPost(postDTO,userId,categoryId);
        return new ResponseEntity<PostDTO>(newPost, HttpStatus.CREATED);
    }

    //Update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){
        PostDTO updatedPost = this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
    }


    //Get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPost(){
        List<PostDTO> posts = this.postService.getAllPost();
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //Get Post By Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
    }

    //Get all post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId){
        List<PostDTO> posts = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //Get all Post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer catgeoryId){
        List<PostDTO> posts = this.postService.getAllPostByCategory(catgeoryId);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //Delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully !!",true),HttpStatus.OK);
    }



}
