package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizcommon.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();

    }

    public Quiz save(Quiz quiz) {
        quiz.setCreatedDateTime(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }


    public Quiz findById(int id) {
        return quizRepository.getById(id);
    }
}
