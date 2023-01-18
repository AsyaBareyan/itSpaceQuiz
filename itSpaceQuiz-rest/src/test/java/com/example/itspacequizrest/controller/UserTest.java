package com.example.itspacequizrest.controller;

import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource("/application.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testSaveUser_whenEmailIsUnique_shouldReturn201() throws Exception {
        // Arrange
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setEmail("test@email.com");
        saveUserRequest.setName("User");
        saveUserRequest.setPassword("password");
        saveUserRequest.setSurname("Test");

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(saveUserRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveUser_whenEmailAlreadyExists_shouldReturn409() throws Exception {
        // Arrange
        SaveUserRequest saveUserRequest = new SaveUserRequest("user",
                "Test", "test@email.com", "password");
        userService.save(saveUserRequest);

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(saveUserRequest)))
                .andExpect(status().isConflict());
    }
}

