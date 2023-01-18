package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizrest.dto.SaveQuestionAndOptionRequest;
import com.example.itspacequizrest.dto.SaveQuizRequest;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application.properties")
class QuestionControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    //    @InjectMocks
//    private QuestionController questionController;
//    @Mock
//    private QuestionRepository questionRepository;
//
//    @Mock
//    private QuestionService questionService;
//
    @MockBean
    private QuestionOptionService questionOptionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private ModelMapper modelMapper;
    private Quiz quiz;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        quiz = new Quiz(1, "quiz11", LocalDateTime.now());
        quizService.save(modelMapper.map(quiz, SaveQuizRequest.class));
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllQuestions() throws Exception {
        addTestQuestions();
        mvc.perform(MockMvcRequestBuilders.get("/question")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        assertEquals(2, questionService.findAll().size());
//        List<QuestionResponseDto> questionResponseDtoList = Arrays.asList(new QuestionResponseDto(),
//                new QuestionResponseDto());
//        when(questionService.findAll()).thenReturn(questionResponseDtoList);
//        ResponseEntity<List<QuestionResponseDto>> response = questionController.getAllQuestions();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(questionResponseDtoList, response.getBody());

    }

    @Test
    void responseAllQuestionsByQuiz() {

    }

    @Test
    void createQuestion() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("id", "4");
        objectNode.put("title", "vorn e HH mayraqaxaqy?");
        objectNode.put("score", "1.0");
        objectNode.put("questionType", "MULTI_SELECT");
        objectNode.put("quizId", "1");
        objectNode.put("title1", "Option 1");
        objectNode.put("title2", "Option 2");
        objectNode.put("title3", "Option 3");
        objectNode.put("title4", "Option 4");
        mvc.perform(post("/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteQuestion() throws Exception {
        int id = 1;
//            doNothing().when(questionService).deleteById(id);

        mvc.perform(delete("/question/{id}", id))
                .andExpect(status().isOk());
        assertFalse(questionRepository.findById(1).isPresent());

//            verify(questionService, times(1)).deleteById(id);
    }


    @Test
    void editQuestion() throws Exception {
            SaveQuestionAndOptionRequest request = new SaveQuestionAndOptionRequest();
            request.setTitle("Test question update");
            request.setTitle1("Option 1");
            request.setTitle2("Option 2");
            request.setTitle3("Option 3");
            request.setTitle4("Option 4");

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(put("/question/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            // Additional asserts can be added here to verify that the question and options were updated correctly.


    }

    private void addTestQuestions() {
        questionRepository.save(Question.builder()
                .id(1)
                .title("Question1")
                .score(1.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(quiz)
                .build());
        questionRepository.save(Question.builder()
                .id(2)
                .title("Question2")
                .score(2.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(quiz)
                .build());
    }
}