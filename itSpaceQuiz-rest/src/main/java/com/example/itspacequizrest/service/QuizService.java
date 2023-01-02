package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizrest.dto.SaveQuizRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public Quiz save(SaveQuizRequest saveQuizRequest) {
        Quiz quiz = modelMapper.map(saveQuizRequest, Quiz.class);
        quiz.setCreatedDateTime(LocalDateTime.now());

        return quizRepository.save(quiz);


    }

    public List<Quiz> findAll() {
        List<Quiz> result = new ArrayList<>(quizRepository.findAll());
        return result;

    }

    public Quiz getById(int id) {

        return quizRepository.getById(id);
    }

    public ResponseEntity deleteById(int id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity update(int id, SaveQuizRequest saveQuizRequest) {
            Optional<Quiz> quiz = quizRepository.findById(id);
            if (!quiz.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Quiz byId = quiz.get();
            byId.setCreatedDateTime(LocalDateTime.now());
            if (saveQuizRequest.getTitle() != null) {
                byId.setTitle(saveQuizRequest.getTitle());
            }

            quizRepository.save(byId);

            return ResponseEntity.ok(quiz);
        }
    }

