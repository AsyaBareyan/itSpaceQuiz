package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.dto.OptionsDto;
import com.example.itspacequizmvc.dto.QuestionWithOptionDto;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;

    @GetMapping("/questions/")
    public String questionsPage(ModelMap map, @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "2") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("title"));
        Page<Question> questions = questionService.findAll(pageRequest);
        map.addAttribute("questions", questions);
        int totalPages = questions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);
        }
        return "questions";
    }


    @PostMapping("/question")
    public String createQuestion(@Valid
                                 @ModelAttribute QuestionWithOptionDto questionWithOptionDto,
                                 BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            return ("saveQuestion");
        } else {
            Question question = questionService.addQuestion(questionWithOptionDto);
            questionOptionService.addOptions(question, questionWithOptionDto);
            int quizId = question.getQuiz().getId();
            map.addAttribute("quiz", quizId);
            return "redirect:/questions/byQuiz/" + quizId;
        }
    }


    @GetMapping("/questions/byQuiz/{id}")
    public String createQuestionPage(ModelMap map, @PathVariable("id") int id) {
        Quiz quiz = quizService.findById(id);
        map.addAttribute("questions", questionService.findAllByQuiz(quiz));
        map.addAttribute("quiz", quiz);
        return "saveQuestion";
    }


    @GetMapping("/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable("id") int id, ModelMap map) {
        int quizId = questionService.findById(id).getQuiz().getId();
        map.addAttribute("quiz", quizId);
        questionService.deleteById(id);
        return "redirect:/questions/byQuiz/" + quizId;
    }

    @GetMapping("/deleteQuestionOption/{id}")
    public String deleteQuestionOption(@PathVariable("id") int id) {
        int questionId = questionOptionService.findById(id).getQuestion().getId();
        questionOptionService.deleteById(id);
        return "redirect:/editQuestion/" + questionId;
    }

    @GetMapping("/editQuestion/{id}")
    public String editQuestionPage(ModelMap map,
                                   @PathVariable("id") int id) {
        Question question = questionService.findById(id);
        map.addAttribute("question", question);
        map.addAttribute("options", questionOptionService.findAllByQuestion(question));
        map.addAttribute("quiz", question.getQuiz());

        return "editQuestion";

    }

    @PostMapping("/editQuestion")
    public String editQuestion(@Valid
                               @ModelAttribute Question question,
                               @Valid @ModelAttribute OptionsDto options,
                               BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            return ("saveQuestion");
        } else {
            Question updateQuestion = questionService.save(question);
            questionOptionService.changeOption(updateQuestion, options);
            int id = question.getQuiz().getId();
            map.addAttribute("quiz", id);
            return "redirect:/questions/byQuiz/" + id;


        }
    }

}

