package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.entity.User;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepo userRepo;

    public UserServiceImplementation(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDTO creteUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        User savedUser = this.userRepo.save(user);
        return this.userToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDTO userDTO1 = this.userToDTO(updatedUser);

        return userDTO1;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        return this.userToDTO(user);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();

        List<UserDTO> userDTOs = users.stream().map(user -> this.userToDTO(user)).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        this.userRepo.delete(user);

    }

    //Conversion of UserDTO to User, can be done using ModelMapping.
    private User dtoToUser(UserDTO userDTO){
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());

        return userDTO;
    }
}
