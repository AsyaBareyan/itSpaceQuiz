package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizrest.dto.QuestionResponseDto;
import com.example.itspacequizrest.dto.QuestionWithOptionDto;
import com.example.itspacequizrest.dto.SaveQuestionAndOptionRequest;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionOptionService questionOptionService;



    @GetMapping("/question")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<QuestionResponseDto> all = questionService.findAll(page, size);
        return ResponseEntity.ok(all);
    }


    @GetMapping("/question/quiz/{id}")
    public ResponseEntity<QuestionWithOptionDto> getListOfQuestionsWitOptionsByQuiz(@PathVariable("id") int id) {
        QuestionWithOptionDto allQuestionsWithOptionsByQuiz = questionService.getAllQuestionsWithOptionsByQuiz(id);

        return ResponseEntity.ok(allQuestionsWithOptionsByQuiz);
    }

    @PostMapping("/question")
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @RequestBody SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        Question question = questionService.addQuestion(saveQuestionAndOptionRequest);
        questionOptionService.addOptions(question, saveQuestionAndOptionRequest.getOptions());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int id) {

        return questionService.deleteById(id);


    }

    @PutMapping("/question/{id}")
    public ResponseEntity editQuestion(@PathVariable("id") int id,
                                       @RequestBody SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        questionService.update(id, saveQuestionAndOptionRequest);
        return ResponseEntity.ok().build();
    }
}

