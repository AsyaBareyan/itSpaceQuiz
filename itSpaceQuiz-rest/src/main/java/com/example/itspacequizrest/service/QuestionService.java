package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizrest.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;
    private final ModelMapper modelMapper;

    public List<QuestionResponseDto> findAll(int page, int size) {
        try {
            Page<Question> questions = questionRepository.findAll(PageRequest.of(page, size));
            return questions.getContent().stream()
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("error occurred while questions are loads", e);
            return Collections.emptyList();
        }
    }

    public ResponseEntity deleteById(int id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            log.info("Question with id {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.error("Question with id {} not found for delete", id);
            return ResponseEntity.notFound().build();
        }
    }


    public Question save(Question question) {

        return questionRepository.save(question);

    }

    public Question addQuestion(SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        Question question = new Question();
        question.setTitle(saveQuestionAndOptionRequest.getTitle());
        question.setScore(saveQuestionAndOptionRequest.getScore());
        question.setQuestionType(saveQuestionAndOptionRequest.getQuestionType());
        question.setQuiz(quizService.getById(saveQuestionAndOptionRequest.getQuizId()));
        Question savedQuestion = save(question);
        log.info("Successfully saved question {} with id {} ", savedQuestion,savedQuestion.getId());
        return savedQuestion;
    }

    public ResponseEntity update(int id, SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question byId = question.get();
        if (saveQuestionAndOptionRequest.getTitle() != null) {
            byId.setTitle(saveQuestionAndOptionRequest.getTitle());
        }
        if (saveQuestionAndOptionRequest.getScore() != null) {
            byId.setScore(saveQuestionAndOptionRequest.getScore());
        }
        if (saveQuestionAndOptionRequest.getQuestionType() != null) {
            byId.setQuestionType(saveQuestionAndOptionRequest.getQuestionType());
        }


        Question updateQuestion = questionRepository.save(byId);
        log.info("Successfully update question with id {}", updateQuestion.getId());
        for (SaveQuestionOptionRequest option : saveQuestionAndOptionRequest.getOptions()) {

            questionOptionService.changeOption(updateQuestion, option);
        }

        QuestionResponseDto questionResponseDto = modelMapper.map(byId, QuestionResponseDto.class);
        return ResponseEntity.ok(questionResponseDto);
    }


    public QuestionWithOptionDto getAllQuestionsWithOptionsByQuiz(int quizId) {
        QuestionWithOptionDto questionWithOptionDto = new QuestionWithOptionDto();
        List<Question> allByQuiz = findAllByQuiz(quizService.getById(quizId));
        for (Question question : allByQuiz) {
            questionWithOptionDto.setQuestion(question);
            List<OptionsDto> optionsDtos = new ArrayList<>();
            List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(question);
            for (QuestionOption questionOption : allByQuestion) {
                optionsDtos.add(modelMapper.map(questionOption, OptionsDto.class));
            }
            questionWithOptionDto.setOptions(optionsDtos);
        }
        return questionWithOptionDto;
    }

    public ResponseEntity <Question> findById(int id) {
        if (!questionRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionRepository.getById(id));

    }

    public List<Question> findAllByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }
}
