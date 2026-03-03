package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.entity.Comment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.payload.CommentDTO;
import com.blog.blog_app.repository.CommentRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {
    private PostRepo postRepo;
    private CommentRepo commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImplementation(PostRepo postRepo, CommentRepo commentRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postId));
        Comment comment = this.modelMapper.map(commentDTO,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","ID",commentId));
        this.commentRepo.delete(comment);
    }
}
