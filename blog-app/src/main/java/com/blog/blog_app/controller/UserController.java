package com.blog.blog_app.controller;

import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createUserDTO = this.userService.createUser(userDTO);

        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }

    //PUT- Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO
                                              , @PathVariable("userId") Integer userId){
        UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
        this.userService.deleteUser(userId);
//        return ResponseEntity.ok(Map.of("message","User Deleted Successful"));
//        return new ResponseEntity(Map.of("message","User Deleted Successful"), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successful",true), HttpStatus.OK);
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
