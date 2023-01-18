package com.example.itspacequizrest.controller;


import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> saveUser( @RequestBody SaveUserRequest saveUserRequest) {
        if(userService.findByEmail(saveUserRequest.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserResponseDto save = userService.save(saveUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);

    }

//    @DeleteMapping("/user/{id}")
//    public ResponseEntity deleteById(@PathVariable("id") int id) {
//        return userService.deleteById(id);
//
//
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") int id) {
//        return userService.getById(id);
//
//
//    }
//
//    @GetMapping("/user")
//    public List<UserResponseDto> getUsers() {
//        return userService.findAll();
//    }


}
