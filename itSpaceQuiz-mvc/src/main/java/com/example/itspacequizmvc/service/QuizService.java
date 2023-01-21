package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizmvc.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        return quizRepository.save(quiz);
    }

    public void deleteById(int id) throws NotFoundException {
        if (quizRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Quiz with id " + id + " not found.");
        }
        quizRepository.deleteById(id);
        log.info("Quiz with id {} deleted", id);
    }


    public Quiz findById(int id) {
        if (!quizRepository.existsById(id)) {
            throw new NoSuchElementException("Quiz not found for provided id.");
        }
        return quizRepository.getById(id);
    }
}
