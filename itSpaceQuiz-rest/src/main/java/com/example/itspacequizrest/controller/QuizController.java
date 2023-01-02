package com.example.itspacequizrest.controller;


import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizrest.dto.SaveQuizRequest;
import com.example.itspacequizrest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final ModelMapper modelMapper;

    @PostMapping("/quiz")
    public Quiz saveQuiz(@RequestBody SaveQuizRequest saveQuizRequest) {
        return quizService.save(saveQuizRequest);

    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity deleteById(@PathVariable("id") int id) {
        return quizService.deleteById(id);


    }
    @GetMapping("/quiz/{id}")
    public Quiz getQuizById(@PathVariable("id") int id) {
        return quizService.getById(id);

    }

    @GetMapping("/quiz")
    public List<Quiz> allQuizzes() {
        return quizService.findAll();
    }

    @PutMapping ("/quiz/{id}")
    public ResponseEntity updateQuiz(@RequestBody SaveQuizRequest saveQuizRequest,
                                     @PathVariable("id") int id) {


        return quizService.update(id,saveQuizRequest);
    }
}
