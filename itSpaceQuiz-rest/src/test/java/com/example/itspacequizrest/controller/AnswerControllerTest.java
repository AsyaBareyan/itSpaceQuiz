package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.ResultDto;
import com.example.itspacequizrest.service.AnswerService;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(AnswerController.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest
class AnswerControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    @MockBean
    private QuizService quizService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionOptionService questionOptionService;

    @MockBean
    private AnswerService answerService;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testCreateAnswer() throws Exception {
//        SaveAnswerRequest saveAnswerRequest = new SaveAnswerRequest();
//        saveAnswerRequest.setUserId(1);
//        saveAnswerRequest.setQuestionId(1);
//        saveAnswerRequest.setQuestionOption(List.of(1, 2));
        Answer answer = new Answer();
        answer.setId(1);
        Quiz quiz = new Quiz();
        quiz.setId(1);

        ResultDto resultDto = new ResultDto();
        resultDto.setResult(10.0);

        when(quizService.getById(1)).thenReturn(quiz);
        when(answerService.saveAndReturn(answer, quiz)).thenReturn(null).thenReturn(resultDto);

        mvc.perform(post("/answer/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answer)))
                .andExpect(status().isOk());
    }
}
