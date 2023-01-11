package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.UserRepository;
import com.example.itspacequizrest.dto.ResultDto;
import com.example.itspacequizrest.dto.SaveAnswerRequest;
import com.example.itspacequizrest.service.AnswerService;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
    public ResponseEntity<Question> createAnswer(@PathVariable("id") int id,
                                                 @RequestBody SaveAnswerRequest saveAnswerRequest) {
        Quiz quiz = quizService.getById(id);
        Answer answer = new Answer();
        answer.setUser(userRepository.getById(saveAnswerRequest.getUserId()));
        answer.setDateTime(LocalDateTime.now());
        Question question = questionService.findById(saveAnswerRequest.getQuestionId());
        answer.setQuestion(question);
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(saveAnswerRequest.getQuestionOption());
        answer.setQuestionOption(questionOptions);
        Question saveAndReturn = answerService.saveAndReturn(answer, quiz);
        List<Question> allQuestionsByQuiz = questionService.findAllByQuiz(quiz);
        List<Answer> allByUser = answerRepository.findAllByUser(answer.getUser());
        List<Answer> byQuiz = new ArrayList<>();
        for (Answer answer1 : allByUser) {
            if (answer1.getQuestion().getQuiz().getId() == quiz.getId()) {
                byQuiz.add(answer1);
            }
        }
        if (saveAndReturn == null) {
            double result = answerService.result(byQuiz);
            double maxResult = answerService.maxResult(allQuestionsByQuiz);
            return ResponseEntity.ok().header("result", String.valueOf(result))
                    .header("maxResult", String.valueOf(maxResult)).build();
        }
        return ResponseEntity.ok(saveAndReturn);
    }

//    @GetMapping("/result")
//    public ResponseEntity <List<Double>> result(double result,double maxResult){
//        List<Double> response= new ArrayList<>();
//        response.add(result);
//        response.add(maxResult);
//        return ResponseEntity.ok(response);
//    }

}





