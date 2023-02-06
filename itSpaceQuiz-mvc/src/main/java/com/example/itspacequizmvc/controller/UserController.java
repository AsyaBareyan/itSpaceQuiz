package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.entity.User;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import com.example.itspacequizmvc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String createUser(@Valid @ModelAttribute User user,
                             BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            return ("saveUser");
        } else {
            userService.save(user);
            return "redirect:/login";
        }
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

}