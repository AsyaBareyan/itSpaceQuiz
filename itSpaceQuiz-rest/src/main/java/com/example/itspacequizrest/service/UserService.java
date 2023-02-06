package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserLoginRequest;
import com.example.itspacequizrest.dto.UserLoginResponse;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserResponseDto save(SaveUserRequest saveUserRequest) {
        User user = modelMapper.map(saveUserRequest, User.class);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setUserType(UserType.STUDENT);

        User savedUser = userRepository.save(user);
        log.info("User saved successfully: {}", savedUser);

        return modelMapper.map(user, UserResponseDto.class);

    }


    public Optional<User> findByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        log.info("User by email {}" + email + " successfully find");
        return byEmail;
    }

    public ResponseEntity<UserLoginResponse> authUser(UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getEmail() != null && !userLoginRequest.getEmail().equals("")) {
            log.info(userLoginRequest.getEmail() + " wants to get a token");
            Optional<User> byEmail = userRepository.findByEmail(userLoginRequest.getEmail());
            if (byEmail.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(), byEmail.get().getPassword())) {
                log.warn("passwords are not matching for user: " + userLoginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            log.info("user with email: {} get the token", userLoginRequest.getEmail());
            return ResponseEntity.ok(new UserLoginResponse(jwtTokenUtil.generateToken(byEmail.get().getEmail())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<UserResponseDto> addUser(SaveUserRequest saveUserRequest) {
        if (findByEmail(saveUserRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserResponseDto save = save(saveUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);

    }
}
