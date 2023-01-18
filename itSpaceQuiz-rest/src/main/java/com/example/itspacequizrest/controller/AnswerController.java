package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.ResultDto;
import com.example.itspacequizrest.dto.SaveAnswerRequest;
import com.example.itspacequizrest.service.AnswerService;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionOptionService questionOptionService;
    private final QuizService quizService;
    private final QuestionService questionService;

    private final UserRepository userRepository;


    @GetMapping("/answer/quiz/{id}")
    public ResponseEntity<Answer> answersByQuiz(@PathVariable("id") int id) {
        List<Answer> byQuiz = new ArrayList<>();
        List<Question> questions = questionService.findAllByQuiz(quizService.getById(id));
        for (Question question : questions) {
            Answer answer = answerService.findByQuestion(question);
            return ResponseEntity.ok(answer);
        }

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/answer/quiz/{id}")
    public ResponseEntity<ResultDto> createAnswer(@PathVariable("id") int id,
                                                  @RequestBody SaveAnswerRequest saveAnswerRequest) {
        Quiz quiz = quizService.getById(id);
        Answer answer = new Answer();
        answer.setUser(userRepository.getById(saveAnswerRequest.getUserId()));
        answer.setDateTime(LocalDateTime.now());
        Question question = questionService.findById(saveAnswerRequest.getQuestionId());
        answer.setQuestion(question);
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(saveAnswerRequest.getQuestionOption());
        answer.setQuestionOption(questionOptions);
        ResultDto resultDto = answerService.saveAndReturn(answer, quiz);
        return ResponseEntity.ok(resultDto);
    }


}





