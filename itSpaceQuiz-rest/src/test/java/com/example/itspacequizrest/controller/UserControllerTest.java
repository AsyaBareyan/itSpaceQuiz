package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebApplicationContext.class)
class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        userRepository.deleteAll();
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    public void saveUser() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();

        objectNode.put("name", "As");
        objectNode.put("surname", "Bar");
        objectNode.put("email", "as@mail.com");
        objectNode.put("password", "bar");


        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().is2xxSuccessful());


    }

    @Test
    void saveUser_EmailDuplicate() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "as");
        objectNode.put("surname", "bareyan");
        objectNode.put("password", "bar");
        objectNode.put("email", "as@mail.com");

        mvc.perform(post("http://localhost:8083/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().is2xxSuccessful());
        assertTrue(userService.findByEmail("as@mail.com").isPresent());
        mvc.perform(post("http://localhost:8083/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isConflict());

    }

}