package com.blog.blog_app.controller;


import com.blog.blog_app.entity.User;
import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> crateUser(@RequestBody UserDTO userDTO){
        UserDTO createUserDTO = this.userService.creteUser(userDTO);

        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }

    //PUT- Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO
                                              , @PathVariable("user") Integer userId){
        UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        this.deleteUser(userId);
//        return ResponseEntity.ok(Map.of("message","User Deleted Successful"));
//        return new ResponseEntity(Map.of("message","User Deleted Successful"), HttpStatus.OK);
        return new ResponseEntity(new ApiResponse("User Deleted Successful",true), HttpStatus.OK);
    }

    //GET - retrieve all users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //GET - retrieve user (by id)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
