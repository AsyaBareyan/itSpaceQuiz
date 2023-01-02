package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionOptionController {
    private final QuestionOptionService questionOptionService;
    private final QuizService quizService;
    private final QuestionService questionService;


    @PostMapping("/questionOption")
    public String createQuestionOption(
            @RequestParam("questionOption") String[] titles) {
        questionOptionService.update(titles);

        return "redirect:/questions";

    }

    @GetMapping("/questionOption/byQuestion/{id}")
    public String createQuestionOptionPage(ModelMap map, @PathVariable("id") int id) {
        Question question = questionService.findById(id);
        List<QuestionOption> options = questionOptionService.findAllByQuestion(question);
        map.addAttribute("options", options);
        map.addAttribute("question", question);
        return "addQuestionOption";

    }


    @GetMapping("/deleteQuestionOption/{id}")
    public String deleteQuestionOption(@PathVariable("id") int id) {
        questionOptionService.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/editQuestionOption/{id}")
    public String editQuestionPage(ModelMap map,
                                   @PathVariable("id") int id) {
        map.addAttribute("questionOption", questionOptionService.findById(id));
        return "addQuestionOption";

    }
}


