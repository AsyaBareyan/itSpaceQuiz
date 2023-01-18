package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;

    @Test
    void save() {
        User user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.STUDENT)
                .build();
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.save(user);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(UserType.STUDENT, result.getUserType());

    }


    @Test
    void findById() {
        int id = 1;
        User user = User.builder()
                .id(id)
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.STUDENT)
                .build();
        when(userRepository.getById(id)).thenReturn(user);
        User result = userService.findById(id);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals("test@gmail.com", result.getEmail());
    }
}