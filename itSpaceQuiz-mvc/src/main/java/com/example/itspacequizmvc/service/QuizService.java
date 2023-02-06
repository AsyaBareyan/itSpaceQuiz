package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();

    }

    public Quiz save(Quiz quiz) {
        quiz.setCreatedDateTime(LocalDateTime.now());
        Quiz savedQuiz = quizRepository.save(quiz);
        log.info("Successfully saved quiz with id {}", savedQuiz.getId());
        return savedQuiz;
    }

    public void deleteById(int id) throws NotFoundException {
        if (quizRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Quiz with id " + id + " not found.");
        }
        quizRepository.deleteById(id);
        log.info("Quiz with id {} deleted", id);
    }


    public Quiz findById(int id) {
        log.info("Attempting to find quiz with id {}", id);
        if (!quizRepository.existsById(id)) {
            log.error("Quiz not found for id {}", id);
            throw new NotFoundException("Quiz not found for provided id.");
        }
        Quiz quiz = quizRepository.getById(id);
        log.info("Successfully found quiz with id {}", id);
        return quiz;
    }
}
