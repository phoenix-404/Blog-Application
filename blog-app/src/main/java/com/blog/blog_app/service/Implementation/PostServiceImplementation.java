package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.entity.Category;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//    public List<PostDTO> getAllPost(Integer pageNumber, Integer pageSize) {
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
//        int pageSize = 10;
//        int pageNumber = 2;

        Pageable pages = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepo.findAll(pages);

        List<Post> allPost = pagePost.getContent();
//        List<Post> posts = this.postRepo.findAll();

        List<PostDTO> postDTOS = allPost.stream().map((post -> this.modelMapper.map(post,PostDTO.class))).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","ID",postId));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
    //  1. Find the user
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));

    //  2. Create the Pageable object
        Pageable pages = PageRequest.of(pageNumber,pageSize);

//      3. Pass Pageable to the repo (returns a Page<Post>)
        Page<Post> postUserPage = this.postRepo.findByUser(user,pages);
//      List<Post> posts = this.postRepo.findByUser(user);

//      4. Get content from the Page object
        List<Post> posts = postUserPage.getContent();

        List<PostDTO> userPostDTOS = posts.stream().map((post -> this.modelMapper.map(post,PostDTO.class))).toList();
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(userPostDTOS);
        postResponse.setPageNumber(postUserPage.getNumber());
        postResponse.setPageSize(postUserPage.getSize());
        postResponse.setTotalElements(postUserPage.getTotalElements());
        postResponse.setTotalPages(postUserPage.getTotalPages());
        postResponse.setLastPage(postUserPage.isLast());

        return postResponse;
//        return postUserDTOS;
    }

    @Override
    public PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","ID", categoryId));

        Pageable pages = PageRequest.of(pageNumber,pageSize);

        Page<Post> postCategoryPage = this.postRepo.findByCategory(category,pages);

        List<Post> posts = postCategoryPage.getContent();
//        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDTO> postCategoryDTOS =  posts.stream().map((post) -> this.modelMapper.map(post,PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postCategoryDTOS);
        postResponse.setPageNumber(postCategoryPage.getNumber());
        postResponse.setPageSize(postCategoryPage.getSize());
        postResponse.setTotalPages(postCategoryPage.getTotalPages());
        postResponse.setTotalElements(postCategoryPage.getTotalElements());
        postResponse.setLastPage(postCategoryPage.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> searchAllPost(String keyword) {
        return List.of();
    }
}
