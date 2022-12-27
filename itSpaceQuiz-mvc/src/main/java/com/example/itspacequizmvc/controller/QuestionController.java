package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;

    private final QuestionOptionService questionOptionService;

    @GetMapping("/questions")
    public String questionsPage(ModelMap map) {
        List<Question> questions = questionService.getAllQuestions();
        map.addAttribute("questions", questions);
        return "questions";
    }

    @PostMapping("/question")
    public String createQuestion(@ModelAttribute Question question,
                                 @RequestParam("title1") String title1,
                                 @RequestParam("title2") String title2,
                                 @RequestParam("title3") String title3,
                                 @RequestParam("title4") String title4) {
        questionService.save(question);
        questionOptionService.saveOptions(question, title1);
        questionOptionService.saveOptions(question,title2);
        questionOptionService.saveOptions(question,title3);
        questionOptionService.saveOptions(question,title4);


        return "redirect:/questions";
    }


    @GetMapping("/questions/byQuiz/{id}")
    public String createQuestionPage(ModelMap map, @PathVariable("id") int id) {
        Quiz quiz = quizService.findById(id);
        List<Question> questions = questionService.findAllByQuiz(quiz);

        map.addAttribute("questions", questions);
        map.addAttribute("quiz", quiz);


        return "saveQuestion";
    }


    @GetMapping("/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/editQuestion/{id}")
    public String editQuestionPage(ModelMap map,
                                   @PathVariable("id") int id) {
        map.addAttribute("question", questionService.findById(id));
        return "saveQuestion";

    }
}


