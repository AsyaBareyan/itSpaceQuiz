package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizrest.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuizControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    @Autowired
    private QuizRepository quizRepository;
    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    @Order(1)
    void all_quizzes_test() throws Exception {
        addTestQuizzes();
        mvc.perform(MockMvcRequestBuilders.get("/quiz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        assertEquals(2, quizRepository.findAll().size());
    }


    @Test
    @Order(2)
    void get_quiz_by_id_test() throws Exception {
//        addTestQuizzes();

        int id = 1;
        mvc.perform(MockMvcRequestBuilders.get("/quiz/{id}", id))
                .andExpect(status().isOk());

        assertTrue(quizRepository.findById(id).isPresent());
    }


    @Test
    @Order(3)
    void save_quiz_test() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("id", 1);
        objectNode.put("title", "spring");

        mvc.perform(MockMvcRequestBuilders.post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());

        assertNotNull(quizRepository.findById(1).isPresent());
    }


    @Test
    @Order(4)
    public void delete_quiz_test() throws Exception {
        addTestQuizzes();
        assertTrue((quizRepository.findById(1).isPresent()));
        mvc.perform(MockMvcRequestBuilders.delete("/quiz/{id}", 1))
                .andExpect(status().isOk());
        assertFalse(quizRepository.findById(1).isPresent());
        mvc.perform(MockMvcRequestBuilders.delete("/quiz/{id}", 1))
                .andExpect(status().is4xxClientError());

    }

    private void addTestQuizzes() {
        quizRepository.save(Quiz.builder()
                .id(1)
                .title("Quiz1")
                .createdDateTime(LocalDateTime.now())
                .build());
        quizRepository.save(Quiz.builder()
                .id(2)
                .title("Quiz2")
                .createdDateTime(LocalDateTime.now())
                .build());

    }
}
