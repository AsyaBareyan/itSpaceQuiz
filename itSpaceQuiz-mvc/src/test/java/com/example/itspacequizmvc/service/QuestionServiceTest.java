package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizmvc.dto.QuestionWithOptionDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@RequiredArgsConstructor
class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizService quizService;

    private Quiz quiz;


    @BeforeEach
    void setUp() {
        quiz = new Quiz(1, "quiz1", LocalDateTime.now());
        quizService.save(quiz);
    }

    @Test
    void save_test() {
        Question question = getQuestion();
        Question expected = questionRepository.save(question);
        Question actual = questionService.save(question);
        assertEquals(expected,actual);
    }


    @Test
    void delete_by_id_test() {
        Question question = getQuestion();
        questionRepository.save(question);
        questionService.deleteById(question.getId());

        assertFalse(questionRepository.findById(question.getId()).isPresent());

    }
    @Test
    public void delete_by_id_doesNotExist_test(){
        assertThrows(NotFoundException.class, () -> questionService.deleteById(10));
    }

    @Test
    @Transactional
    void find_by_id_test() {
        Question question = questionRepository.save(getQuestion());
        Question actualFindByIdQuestion = questionService.findById(question.getId());
        assertEquals(questionRepository.getById(question.getId()), actualFindByIdQuestion);
    }

    @Test
    public void find_by_id_doesNotExist_test(){
        assertThrows(NotFoundException.class, () -> questionService.findById(10));
    }

    @Test
    void findAllByQuiz() {
        questionRepository.save(getQuestion());

        assertEquals(1,questionService.findAllByQuiz(quiz).size());
    }

    @Test
    void addQuestion() {
        QuestionWithOptionDto questionWithOptionDto = new QuestionWithOptionDto();
        questionWithOptionDto.setTitle("question 2?");
        questionWithOptionDto.setScore(1.0);
        questionWithOptionDto.setQuestionType(QuestionType.SINGLE_SELECT);
        questionWithOptionDto.setQuiz(quiz);

        Question savedQuestion = questionService.addQuestion(questionWithOptionDto);

        assertNotNull(savedQuestion.getId());
        assertEquals(questionWithOptionDto.getTitle(), savedQuestion.getTitle());
        assertEquals(questionWithOptionDto.getScore(), savedQuestion.getScore());
        assertEquals(questionWithOptionDto.getQuestionType(), savedQuestion.getQuestionType());
        assertEquals(questionWithOptionDto.getQuiz().getId(), savedQuestion.getQuiz().getId());
    }


    private Question getQuestion() {
        return Question.builder()
                .id(1)
                .title("Question 1?")
                .score(5.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(quiz)
                .build();
    }
}