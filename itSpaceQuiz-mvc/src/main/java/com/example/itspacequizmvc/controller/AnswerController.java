package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.dto.ResultDto;
import com.example.itspacequizmvc.security.CurrentUser;
import com.example.itspacequizmvc.service.AnswerService;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;
    private final QuestionService questionService;


    @PostMapping("/user/quiz/{id}")
    public String answer(@PathVariable("id") int id, @ModelAttribute Answer answer,
                         @RequestParam("qOption") List<Integer> options,
                         ModelMap map) {
        Quiz quiz = quizService.findById(id);
        ResultDto resultDto = answerService.addAnswer(quiz, answer, options);
        if (resultDto.getQuestion() == null) {
            map.addAttribute("result", resultDto.getResult());
            map.addAttribute("maxResult", resultDto.getMaxResult());
            return "result";
        }
        map.addAttribute("question", resultDto.getQuestion());
        map.addAttribute("quiz", quiz);
        map.addAttribute("options", questionOptionService.findAllByQuestion(resultDto.getQuestion()));
        map.addAttribute("user", answer.getUser());
        return "answer";
    }


    @GetMapping("/user/quiz/{id}")
    public String createAnswerPage(ModelMap map, @PathVariable("id") int id,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        Quiz quiz = quizService.findById(id);
        List<Question> questions = questionService.findAllByQuiz(quiz);
        map.addAttribute("questions", questions);
        map.addAttribute("quiz", quiz);
        map.addAttribute("user", currentUser.getUser());

        for (Question question : questions) {
            map.addAttribute("question", question);
            map.addAttribute("options", questionOptionService.findAllByQuestion(question));
        }

        return "answer";
    }


}
