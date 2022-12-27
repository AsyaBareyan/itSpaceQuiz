package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.*;
import com.example.itspacequizcommon.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuestionOptionService questionOptionService;

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user) {
        userService.save(user);
        return "user";
    }

    @GetMapping("/register")
    public String createUserPage() {
        return "saveUser";
    }

    @GetMapping("")
    public String userPage(ModelMap map) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        map.addAttribute("quizzes", quizzes);
        return "user";

    }

    @GetMapping("/quiz/{id}")
    public String createAnswerPage(ModelMap map, @PathVariable("id") int id) {
        Quiz quiz = quizService.findById(id);

        List<Question> questions = questionService.findAllByQuiz(quiz);
        map.addAttribute("questions", questions);
        map.addAttribute("quiz", quiz);

        for (Question question : questions) {

            List<QuestionOption> options = questionOptionService.findAllByQuestion(question);
            if (!options.isEmpty()) {
                map.addAttribute("options", options);
            }
            map.addAttribute("question", question);
return "answer";
        }

        return "answer";
    }
}