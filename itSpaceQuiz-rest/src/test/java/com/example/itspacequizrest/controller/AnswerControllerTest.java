package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizrest.dto.SaveAnswerRequest;
import com.example.itspacequizrest.service.AnswerService;
import com.example.itspacequizrest.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest
class AnswerControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private QuizService quizService;

    @Autowired
    private AnswerService answerService;

    @BeforeEach
    public void setup() {

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void create_answer_test() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1);

        int quizId = 1;
        String url = "/answer/quiz/" + quizId;

        SaveAnswerRequest saveAnswerRequest = new SaveAnswerRequest();


//        String requestJson = objectMapper.writeValueAsString(saveAnswerRequest);
//
//        String response = mockMvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        ResultDto resultDto = objectMapper.readValue(response, ResultDto.class);
//        assertThat(resultDto).isNotNull();
//    }

//        ResultDto resultDto = new ResultDto();
//        resultDto.setResult(10.0);
//
//        when(quizService.getById(1)).thenReturn(quiz);
//        when(answerService.saveAndReturn(answer, quiz)).thenReturn(null).thenReturn(resultDto);
//
//        mvc.perform(post("/answer/quiz/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(answer)))
//                .andExpect(status().isOk());
//    }
    }
}
