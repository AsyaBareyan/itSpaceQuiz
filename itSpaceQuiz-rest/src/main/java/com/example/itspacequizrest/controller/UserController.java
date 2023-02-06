package com.example.itspacequizrest.controller;


import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserLoginRequest;
import com.example.itspacequizrest.dto.UserLoginResponse;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/user/auth")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.authUser(userLoginRequest);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        return userService.addUser(saveUserRequest);
    }
}
