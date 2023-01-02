package com.example.itspacequizrest.controller;

import com.example.itspacequizrest.dto.QuestionResponseDto;
import com.example.itspacequizrest.dto.SaveQuestionRequest;
import com.example.itspacequizrest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question")
    public List<QuestionResponseDto> getAllQuestions() {

        return questionService.findAll();
    }

    @PostMapping("/question")
    public QuestionResponseDto createQuestion(@RequestBody SaveQuestionRequest saveQuestionRequest) {
        return questionService.save(saveQuestionRequest);
    }


    @DeleteMapping("/question/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int id) {

        return questionService.deleteById(id);


    }

    @PutMapping("/question/{id}")
    public ResponseEntity editQuestion(@PathVariable("id") int id,
                                       @RequestBody SaveQuestionRequest saveQuestionRequest) {
        return questionService.update(id, saveQuestionRequest);


    }
}

