package com.blog.blog_app.repository;


import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
