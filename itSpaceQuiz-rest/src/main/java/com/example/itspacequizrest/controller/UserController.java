package com.example.itspacequizrest.controller;


import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/user")
    public UserResponseDto saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        return userService.save(saveUserRequest);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteById(@PathVariable("id") int id) {
        return userService.deleteById(id);


    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") int id) {
        return userService.getById(id);


    }

    @GetMapping("/user")
    public List<UserResponseDto> getUsers() {
        return userService.findAll();
    }


}
