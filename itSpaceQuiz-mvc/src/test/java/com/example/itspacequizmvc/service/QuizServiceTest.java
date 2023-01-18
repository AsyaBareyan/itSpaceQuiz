package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    void getAllQuizzes() {
        Quiz quiz1 = Quiz.builder().id(1).title("Quiz 1").build();
        Quiz quiz2 = Quiz.builder().id(2).title("Quiz 2").build();
        when(quizRepository.findAll()).thenReturn(Arrays.asList(quiz1, quiz2));

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertEquals(2, quizzes.size());
        assertTrue(quizzes.contains(quiz1));
        assertTrue(quizzes.contains(quiz2));
    }

    @Test
    void save() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1")
                .createdDateTime(LocalDateTime.now()).build();
        when(quizRepository.save(quiz)).thenReturn(quiz);

        Quiz savedQuiz = quizService.save(quiz);

        assertEquals(quiz, savedQuiz);

    }

    @Test
    void deleteById() {
        quizService.deleteById(1);
        verify(quizRepository).deleteById(1);
    }

    @Test
    void findById() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1").build();
        when(quizRepository.getById(anyInt())).thenReturn(quiz);

        Quiz foundQuiz = quizService.findById(1);

        assertEquals(quiz, foundQuiz);
    }
}
