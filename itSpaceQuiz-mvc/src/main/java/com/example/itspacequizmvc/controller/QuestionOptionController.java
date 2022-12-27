package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionOptionController {
    private final QuestionOptionService questionOptionService;
    private final QuizService quizService;
    private final QuestionService questionService;


    @PostMapping("/questionOption")
    public String createQuestionOption(
//            @RequestParam("radio1") String radio1,
//            @RequestParam("radio2") String radio2,
//            @RequestParam("radio3") String radio3,
            @RequestParam("questionOption") String[] titles) {
        questionOptionService.update(titles);
//        questionOptionService.update(titles, radio2);
//        questionOptionService.update(titles, radio3);


        return "redirect:/questions";

    }

    @GetMapping("/questionOption/byQuestion/{id}")
    public String createQuestionOptionPage(ModelMap map, @PathVariable("id") int id) {
        Question question = questionService.findById(id);
        List<QuestionOption> options = questionOptionService.findAllByQuestion(question);
//        if (question.getQuestionType() == QuestionType.SINGLE_SELECT)
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


