package com.blog.blog_app.repository;

import com.blog.blog_app.entity.Category;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

//    List<Post> findByUser(User user);
    Page<Post> findByUser(User user, Pageable pageable);
//    List<Post> findByCategory(Category category);
    Page<Post> findByCategory(Category category, Pageable pageable);
}

