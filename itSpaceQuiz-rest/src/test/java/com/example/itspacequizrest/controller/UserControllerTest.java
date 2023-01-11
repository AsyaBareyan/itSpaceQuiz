package com.example.itspacequizrest.controller;

import com.example.itspacequizrest.dto.SaveUserRequest;
import com.example.itspacequizrest.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @MockBean
    UserService userService;
    //    @MockBean
//    UserController userController;

    ObjectMapper objectMapper;


    @AfterEach
    void tearDown() {
    }

    @Test
    void saveUser() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "As");
        objectNode.put("surname", "Bar");
        objectNode.put("email", "as@mail.com");
        objectNode.put("password", "bar");


        mvc.perform(post("http://localhost:8083/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().is2xxSuccessful());


    }

//    @Test
//    public void testSaveUser() throws Exception {
//        SaveUserRequest saveUserRequest = new SaveUserRequest();
//
//        saveUserRequest.setName("Asya");
//        saveUserRequest.setSurname("Bareyan");
//        saveUserRequest.setEmail("Asya@mail.ru");
//        saveUserRequest.setPassword("bar");
//
//        String jsonRequest = objectMapper.writeValueAsString(saveUserRequest);
//
//        mvc.perform(post("/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.email").value(saveUserRequest.getEmail()))
//                .andExpect(jsonPath("$.firstName").value(saveUserRequest.getName()))
//                .andExpect(jsonPath("$.lastName").value(saveUserRequest.getSurname()));
//    }

    @Test
    void saveUser_EmailDuplicate() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "poxosik");
        objectNode.put("surname", "poxosyan");
        objectNode.put("password", "poxos");
        objectNode.put("email", "poxosik@mail.com");

//        mvc.perform(post("http://localhost:8083/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectNode.toString()))
//                .andExpect(status().isOk());

        mvc.perform(post("http://localhost:8083/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isConflict());

    }

    @Test
    void deleteById() {
    }

    @Test
    void getUserById() {
    }
}