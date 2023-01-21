package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/quizzes")
    public String quizPage(ModelMap map) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        map.addAttribute("quizzes", quizzes);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String createQuiz(@Valid @ModelAttribute Quiz quiz,
                             BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            return ("saveQuiz");
        } else {
            quizService.save(quiz);
            return "redirect:/quizzes";

        }
    }


    @GetMapping("/quizCreate")
    public String createQuizPage() {
        return "saveQuiz";
    }

    @GetMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable("id") int id) {
        quizService.deleteById(id);
        return "redirect:/quizzes";
    }

    @GetMapping("/editQuiz/{id}")
    public String editQuizPage(ModelMap map,
                               @PathVariable("id") int id) {
        map.addAttribute("quiz", quizService.findById(id));
        return "saveQuiz";

    }

}
