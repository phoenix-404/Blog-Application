package com.blog.blog_app.controller;

import com.blog.blog_app.config.AppConstants;
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
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
//        List<PostDTO> posts = this.postService.getAllPost(pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
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
                                                       @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
                                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
//        List<PostDTO> posts = this.postService.getAllPostByUser(userId,pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPostByUser(userId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Get all Post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
                                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
//        List<PostDTO> posts = this.postService.getAllPostByCategory(categoryId,pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully !!",true),HttpStatus.OK);
    }

    //Search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keyword") String keywords){
        List<PostDTO> posts = this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }
}
