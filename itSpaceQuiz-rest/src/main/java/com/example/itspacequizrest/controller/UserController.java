package com.example.itspacequizrest.controller;


import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserLoginRequest;
import com.example.itspacequizrest.dto.UserLoginResponse;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.service.UserService;
import com.example.itspacequizrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/user/auth")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getEmail() != null && !userLoginRequest.getEmail().equals("")) {
            log.info(userLoginRequest.getEmail() + " wants to get a token");
            Optional<User> byEmail = userRepository.findByEmail(userLoginRequest.getEmail());
            if (!byEmail.isPresent() || !passwordEncoder.matches(userLoginRequest.getPassword(), byEmail.get().getPassword())) {
                log.warn("passwords are not matching for user: " + userLoginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            log.info("user with email: {} get the token", userLoginRequest.getEmail());
            return ResponseEntity.ok(new UserLoginResponse(jwtTokenUtil.generateToken(byEmail.get().getEmail())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

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
