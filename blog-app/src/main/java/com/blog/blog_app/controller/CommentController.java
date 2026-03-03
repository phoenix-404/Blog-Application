package com.blog.blog_app.controller;

import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.CommentDTO;
import com.blog.blog_app.service.CommentService;
import com.blog.blog_app.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private PostService postService;
    private CommentService commentService;

    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId){
        CommentDTO newCommentDTO = this.commentService.createComment(commentDTO,postId);
        return new ResponseEntity<>(newCommentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/comment/{commentId}")
    ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully !", true), HttpStatus.OK);
    }
}
