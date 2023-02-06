package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSave() {
        User user = getUser();

        User savedUser = userService.save(user);
        assertEquals("Poxos@mail.ru", savedUser.getEmail());
        assertTrue(passwordEncoder.matches("Poxos", savedUser.getPassword()));
        assertEquals(UserType.STUDENT, savedUser.getUserType());
    }

    private User getUser() {
        User user = new User();
        user.setName("Poxos");
        user.setSurname("Poxosyan");
        user.setEmail("Poxos@mail.ru");
        user.setPassword("Poxos");
        user.setUserType(UserType.STUDENT);
        return user;
    }

    @Test
    @Transactional
    public void findById() {

        User expectedUser = getUser();
        expectedUser.setId(1);
        userRepository.save(expectedUser);

        User actualUser = userService.findById(1);

        assertEquals(expectedUser, actualUser);

    }
}