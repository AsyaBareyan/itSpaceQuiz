package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.*;
import com.example.itspacequizcommon.repository.*;
import com.example.itspacequizmvc.dto.ResultDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AnswerServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionOptionRepository questionOptionRepository;
    @Autowired
    private QuestionOptionService questionOptionService;

    @Autowired
    private AnswerService answerService;
    private QuestionOption option1,option2;


    @Test
    @Transactional
    void save_and_return_when_NotAnsweredAllQuestions() {
        Quiz quiz = getQuiz();
        User user = getUser();
        Question question1 = getQuestion1();
        Question question2 = getQuestion2();
        option1 = new QuestionOption();
        option1.setTitle("Option 1");
        option1.setCorrect(true);
        option1.setQuestion(question1);
        questionOptionRepository.save(option1);
        option2 = new QuestionOption();
        option2.setTitle("Option 2");
        option2.setCorrect(true);
        option2.setQuestion(question2);

        Answer answer1=new Answer();
        answer1.setUser(user);
        answer1.setQuestion(question1);
        answer1.setDateTime(LocalDateTime.now());
        answer1.setQuestionOption(new ArrayList<>(questionOptionRepository.save(option1).getId()));
        Answer saveAnswer = answerRepository.save(answer1);
        ResultDto resultDto = answerService.saveAndReturn(saveAnswer, quiz);
        assertEquals(question2,resultDto.getQuestion());
        assertEquals(0.0, resultDto.getResult());
        assertEquals(0.0, resultDto.getMaxResult());


    }

    @Test
    @Transactional
    void save_and_return_when_AnsweredAllQuestions() {
        Quiz quiz = getQuiz();
        User user = getUser();
        Question question1 = getQuestion1();
        Question question2 = getQuestion2();
        option1 = new QuestionOption();
        option1.setTitle("Option 1");
        option1.setCorrect(true);
        option1.setQuestion(question1);
        questionOptionRepository.save(option1);
        option2 = new QuestionOption();
        option2.setTitle("Option 2");
        option2.setCorrect(true);
        option2.setQuestion(question2);
        questionOptionRepository.save(option2);

        Answer answer1=new Answer();
        answer1.setUser(user);
        answer1.setQuestion(question1);
        answer1.setDateTime(LocalDateTime.now());
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(Arrays.asList(option1.getId()));
        answer1.setQuestionOption(questionOptions);
        answerRepository.save(answer1);
        Answer answer2=new Answer();
        answer2.setUser(user);
        answer2.setQuestion(question2);
        answer2.setDateTime(LocalDateTime.now());
        List<QuestionOption> questionOptions2 = questionOptionService.addAnswerOptions(Arrays.asList(option2.getId()));
        answer2.setQuestionOption(questionOptions2);
        Answer saveAnswer2 = answerRepository.save(answer2);
        ResultDto resultDto = answerService.saveAndReturn(saveAnswer2, quiz);
        assertNull(resultDto.getQuestion());
        assertEquals(15.0, resultDto.getResult());
        assertEquals(15.0, resultDto.getMaxResult());
    }

    private User getUser() {
        User user = new User();
        user.setName("Poxos");
        user.setSurname("Poxosyan");
        user.setEmail("Poxos@mail.ru");
        user.setPassword("Poxos");
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }

    private Quiz getQuiz() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1").createdDateTime(LocalDateTime.now()).build();
        return quizRepository.save(quiz);
    }

    private Question getQuestion1() {
        Question question1 = Question.builder()
                .id(1)
                .title("Question 1?")
                .score(10.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(getQuiz())
                .build();
        return questionRepository.save(question1);
    }

    private Question getQuestion2() {
        Question question2 = Question.builder()
                .id(2)
                .title("Question 2?")
                .score(5.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(getQuiz())
                .build();
        return questionRepository.save(question2);
    }
}