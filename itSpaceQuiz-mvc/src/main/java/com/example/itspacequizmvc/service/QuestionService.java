package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();

    }

    public Question save(Question question) {


        return questionRepository.save(question);
    }


    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }


    public Question findById(int id) {
        return questionRepository.getById(id);
    }


    public List<Question> findAllByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }


    public Question findByQuiz(Quiz quiz){
        return questionRepository.findByQuiz(quiz);
    }


    }

