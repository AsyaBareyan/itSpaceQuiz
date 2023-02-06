package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuizRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")

class QuizServiceTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @Order(2)
    @Test
    void getAllQuizzes() {
        Quiz quiz1 = Quiz.builder().id(1).title("Quiz 1").build();
        Quiz quiz2 = Quiz.builder().id(2).title("Quiz 2").build();
//        quizRepository.findAll();
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertEquals(2, quizzes.size());
        assertTrue(quizzes.contains(quiz1));
        assertTrue(quizzes.contains(quiz2));
    }

    @Test
    @Order(1)
    void save() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1")
                .createdDateTime(LocalDateTime.now()).build();
        quizRepository.save(quiz);

        Quiz savedQuiz = quizService.save(quiz);

        assertEquals(quiz, savedQuiz);

    }

    @Order(4)
    @Test
    void deleteById() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1")
                .createdDateTime(LocalDateTime.now()).build();
        quizRepository.save(quiz);
        int id = quiz.getId();
        assertTrue(quizRepository.findById(id).isPresent());
        quizService.deleteById(id);
        assertTrue(quizRepository.findById(id).isEmpty());
    }

    @Test
    public void deleteById_NotExist() {
        int id = 1;
        assertThrows(NotFoundException.class, () -> {
            quizService.deleteById(id);
        });
    }

    @Order(3)
    @Test
    void findById() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1")
                .createdDateTime(LocalDateTime.now()).build();
        quizRepository.save(quiz);
        assertTrue(quizRepository.findById(1).isPresent());
    }
}
