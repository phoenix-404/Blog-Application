package com.blog.blog_app.service;

import com.blog.blog_app.payload.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO creteUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, Integer userId);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Integer userId);

}
