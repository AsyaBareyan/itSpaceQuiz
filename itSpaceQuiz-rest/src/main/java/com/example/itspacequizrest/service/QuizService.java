package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizrest.dto.SaveQuizRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public Quiz save(SaveQuizRequest saveQuizRequest) {
        Quiz quiz = modelMapper.map(saveQuizRequest, Quiz.class);
        quiz.setCreatedDateTime(LocalDateTime.now());
        Quiz savedQuiz = quizRepository.save(quiz);
        log.info("Quiz successfully saved {} ", savedQuiz);
        return savedQuiz;
    }

    public List<Quiz> findAll() {
        return new ArrayList<>(quizRepository.findAll());

    }

    public Quiz getById(int id) {
        log.info("Attempting to find quiz with id {}", id);
        if (!quizRepository.existsById(id)) {
            log.error("Quiz not found for id {}", id);
            throw new NotFoundException("Quiz not found for provided id.");
        }

        Quiz quiz = quizRepository.getById(id);
        log.info("Successfully found quiz{} with id {}", quiz, id);
        return quiz;
    }

    public ResponseEntity deleteById(int id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            log.info("Quiz with id {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.error("Quiz not found for delete with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity update(int id, SaveQuizRequest saveQuizRequest) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz byId = quiz.get();
        byId.setCreatedDateTime(LocalDateTime.now());
        if (saveQuizRequest.getTitle() != null) {
            byId.setTitle(saveQuizRequest.getTitle());
        }

        quizRepository.save(byId);
        log.info("Successfully update quiz with id {}", id);

        return ResponseEntity.ok(quiz);
    }
}

