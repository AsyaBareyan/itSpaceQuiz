package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizrest.dto.ResultDto;
import com.example.itspacequizrest.dto.SaveAnswerRequest;
import com.example.itspacequizrest.security.CurrentUser;
import com.example.itspacequizrest.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/answer/quiz/{id}")
    public ResponseEntity<Answer> answersByQuiz(@PathVariable("id") int id) {
        return ResponseEntity.ok(answerService.answersByQuiz(id));
    }


    @PostMapping("/answer/quiz/{id}")
    public ResponseEntity<ResultDto> createAnswer(@PathVariable("id") int quizId, @AuthenticationPrincipal CurrentUser currentUser,
                                                  @RequestBody SaveAnswerRequest saveAnswerRequest) {

        ResultDto resultDto = answerService.createAnswer(quizId, currentUser, saveAnswerRequest);

        return ResponseEntity.ok(resultDto);
    }


}





