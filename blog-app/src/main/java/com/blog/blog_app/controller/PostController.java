package com.blog.blog_app.controller;

import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;
import com.blog.blog_app.service.PostService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDTO newPost = this.postService.createPost(postDTO,userId,categoryId);
        return new ResponseEntity<PostDTO>(newPost, HttpStatus.CREATED);
    }

    //Update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId){
        PostDTO updatedPost = this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
    }


    //Get all posts
//    @RequestParam: Extracts values from the query string (after the ?).
    @GetMapping("/posts")
//    public ResponseEntity<List<PostDTO>> getAllPost(
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
    ){
//        List<PostDTO> posts = this.postService.getAllPost(pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Get Post By Id
//   @PathVariable: Extracts values from the URI path itself (part of the clean URL)
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
    }

    //Get all post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize){
//        List<PostDTO> posts = this.postService.getAllPostByUser(userId,pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPostByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Get all Post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber", defaultValue = "1",required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize){
//        List<PostDTO> posts = this.postService.getAllPostByCategory(catgeoryId,pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPostByUser(categoryId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully !!",true),HttpStatus.OK);
    }



}
