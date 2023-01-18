package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionOptionRepository questionOptionRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void testSave() {
        Question question = new Question();
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question result = questionService.save(question);

        assertEquals(question, result);
    }

    @Test
    void testDeleteById() {
        questionService.deleteById(1);
    }

    @Test
    void testFindById() {
        Question question = new Question();
        when(questionRepository.getById(1)).thenReturn(question);

        Question result = questionService.findById(1);

        assertEquals(question, result);
    }

    @Test
    void testFindAllByQuiz() {
        Quiz quiz = new Quiz();
        List<Question> questions = List.of(new Question());
        when(questionRepository.findAllByQuiz(quiz)).thenReturn(questions);

        List<Question> result = questionService.findAllByQuiz(quiz);

        assertEquals(questions, result);
    }

    @Test
    void testFindAll() {
        Page<Question> questions = Page.empty();
        when(questionRepository.findAll(pageable)).thenReturn(questions);

        Page<Question> result = questionService.findAll(pageable);

        assertEquals(questions, result);
    }
}
