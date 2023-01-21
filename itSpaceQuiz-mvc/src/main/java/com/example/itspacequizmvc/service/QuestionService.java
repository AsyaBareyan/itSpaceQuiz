package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizmvc.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;


    public Question save(Question question) {


        return questionRepository.save(question);
    }


    public void deleteById(int id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new NotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
        log.info("Question with id {} deleted", id);

    }


    public Question findById(int id) {
        if (!questionRepository.existsById(id)){
            throw new NotFoundException("Question not found for provided id.");
        }
        return questionRepository.getById(id);
    }


    public List<Question> findAllByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }

    public Page<Question> findAll(Pageable pageable) {

        return questionRepository.findAll(pageable);
    }


}

