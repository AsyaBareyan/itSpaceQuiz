package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("user whit {} email, already exist",user.getEmail());
            throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setUserType(UserType.STUDENT);

        User savedUser = userRepository.save(user);
        log.info("User saved successfully: {}", savedUser);
        return savedUser;

    }


    public User findById(int id) {
        log.info("Attempting to find user with id {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User not found for id {}", id);
            throw new NotFoundException("user with provided id not found");
        }
        User user = userRepository.getById(id);
        log.info("Successfully found user with id {}", id);
        return user;
    }

}
