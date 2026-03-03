package com.blog.blog_app.controller;

import com.blog.blog_app.config.AppConstants;
import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.ImageResponse;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;
import com.blog.blog_app.service.FileService;
import com.blog.blog_app.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    private PostService postService;

    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
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

    //Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId) throws IOException{
        PostDTO postDTO = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);

        postDTO.setImage(fileName);
        PostDTO updatePost = this.postService.updatePost(postDTO,postId);

        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

//        return new ResponseEntity<>(new ImageResponse(fileName,"Image Uploaded Successfully!!"), HttpStatus.OK);
//        try {
//            fileName= this.fileService.uploadImage(path,image);
//        } catch (IOException e){
//            return new ResponseEntity<>(new ImageResponse(null,"Image Upload Failed!"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    //Post Image Upload
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException{
        //1. Get the stream from service
        InputStream resource = this.fileService.getResource(path, imageName);

        //2. Set the response content type
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        //3. Copy the stream to the response output stream
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
