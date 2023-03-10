package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizmvc.dto.QuestionWithOptionDto;
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
        if (!questionRepository.existsById(id)) {
            log.error("Question not found for id {}", id);
            throw new NotFoundException("Question not found for provided id.");
        }
        Question byId = questionRepository.getById(id);
        log.info("Successfully found question with id {}", id);
        return byId;

    }


    public List<Question> findAllByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }

    public Page<Question> findAll(Pageable pageable) {

        return questionRepository.findAll(pageable);
    }


    public Question addQuestion(QuestionWithOptionDto questionWithOptionDto) {
        Question question = new Question();
        question.setTitle(questionWithOptionDto.getTitle());
        question.setScore(questionWithOptionDto.getScore());
        question.setQuestionType(questionWithOptionDto.getQuestionType());
        question.setQuiz(questionWithOptionDto.getQuiz());
        Question savedQuestion = save(question);
        log.info("Successfully save question with id {}", savedQuestion.getId());
        return savedQuestion;

    }
}

