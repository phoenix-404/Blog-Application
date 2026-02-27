package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.entity.Category;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    private PostRepo postRepo;
    private ModelMapper modelMapper;
    private UserRepo userRepo;
    private CategoryRepo categoryRepo;

    public PostServiceImplementation(PostRepo postRepo, ModelMapper modelMapper,
                                     UserRepo userRepo, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID", categoryId));

        Post post = this.modelMapper.map(postDTO,Post.class);

        post.setImageName("default.png");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost =  this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","ID",postId));

        post.setPostTitle(postDTO.getPostTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImage());

        this.postRepo.save(post);
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","ID",postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = this.postRepo.findAll();

        List<PostDTO> postDTOS = posts.stream().map((post -> this.modelMapper.map(post,PostDTO.class))).toList();
        return postDTOS;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postId));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDTO> postUserDTOS = posts.stream().map((post -> this.modelMapper.map(post,PostDTO.class))).toList();
        return postUserDTOS;
    }

    @Override
    public List<PostDTO> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDTO> postCategoryDTOS =  posts.stream().map((post) -> this.modelMapper.map(post,PostDTO.class)).toList();
        return postCategoryDTOS;
    }

    @Override
    public List<PostDTO> searchAllPost(String keyword) {
        return List.of();
    }
}
