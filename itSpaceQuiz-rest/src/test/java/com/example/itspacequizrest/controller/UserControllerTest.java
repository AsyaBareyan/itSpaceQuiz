package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.dto.UserLoginRequest;
import com.example.itspacequizrest.dto.UserLoginResponse;
import com.example.itspacequizrest.dto.UserResponseDto;
import com.example.itspacequizrest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    void user_login_test() throws Exception {
//        addTestUser();
        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("poxos@mail.ru");
        request.setPassword("poxos");

        UserLoginResponse response = new UserLoginResponse();
        response.setToken("test_token");

        when(userService.authUser(any(UserLoginRequest.class)))
                .thenReturn(ResponseEntity.ok(response));

        mvc.perform(post("/user/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void save_user_test() throws Exception {
        SaveUserRequest request = new SaveUserRequest();
        request.setName("Asya");
        request.setSurname("Bareyan");
        request.setEmail("asyabareyan@gmail.com");
        request.setPassword("bar");

        UserResponseDto response = new UserResponseDto();
        response.setId(1);


        when(userService.addUser(any(SaveUserRequest.class)))
                .thenReturn(ResponseEntity.ok(response));

                mvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());

    }

    private void addTestUser() {
        userRepository.save(User.builder()
                .id(1)
                .name("Poxos")
                .surname("Poxosyan")
                .email("poxos@mail.ru")
                .password("poxos")
                .userType(UserType.STUDENT)
                .build());


    }
}
