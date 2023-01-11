package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizrest.dto.SaveQuestionOptionRequest;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionOptionController {
    private final QuestionOptionService questionOptionService;

    private final QuestionService questionService;


    @PostMapping("/questionOption")
    public ResponseEntity createQuestionOption(
            @RequestBody SaveQuestionOptionRequest saveQuestionOptionRequest) {

        Question question = questionService.findById(saveQuestionOptionRequest.getQuestionId());
        questionOptionService.saveOptions(question, saveQuestionOptionRequest.getTitles()[0]);
        questionOptionService.saveOptions(question, saveQuestionOptionRequest.getTitles()[1]);
        questionOptionService.saveOptions(question, saveQuestionOptionRequest.getTitles()[2]);
        questionOptionService.saveOptions(question, saveQuestionOptionRequest.getTitles()[3]);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/questionOption")
    public ResponseEntity<QuestionOption> updateQuestionOption(
            @RequestBody SaveQuestionOptionRequest saveQuestionOptionRequest,
            @RequestParam("title1") String title1,
            @RequestParam("title2") String title2,
            @RequestParam("title3") String title3,
            @RequestParam("title4") String title4) {

        Question question = questionService.findById(saveQuestionOptionRequest.getQuestionId());
        List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(question);
        questionOptionService.editOptions(question, allByQuestion.get(0), title1);
        questionOptionService.editOptions(question, allByQuestion.get(1), title2);
        questionOptionService.editOptions(question, allByQuestion.get(2), title3);
        questionOptionService.editOptions(question, allByQuestion.get(3), title4);

        QuestionOption questionOption = questionOptionService.changeOption(allByQuestion);
        return ResponseEntity.ok(questionOption);
    }

}


