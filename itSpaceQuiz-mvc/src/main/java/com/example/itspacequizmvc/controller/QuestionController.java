package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String createQuestion(
            @ModelAttribute Question question, ModelMap map,
            @RequestParam("title1") String title1,
            @RequestParam("title2") String title2,
            @RequestParam("title3") String title3,
            @RequestParam("title4") String title4) {
        questionService.save(question);
        questionOptionService.saveOptions(question, title1);
        questionOptionService.saveOptions(question, title2);
        questionOptionService.saveOptions(question, title3);
        questionOptionService.saveOptions(question, title4);


        int id = question.getQuiz().getId();
        map.addAttribute("quiz", id);

        return "redirect:/questions/byQuiz/" + id;
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
    public String deleteQuestion(@PathVariable("id") int id, ModelMap map) {
        int id1 = questionService.findById(id).getQuiz().getId();
        map.addAttribute("quiz", id1);
        questionService.deleteById(id);


        return "redirect:/questions/byQuiz/" + id1;
    }

    @GetMapping("/editQuestion/{id}")
    public String editQuestionPage(ModelMap map,
                                   @PathVariable("id") int id) {
        Question question = questionService.findById(id);
        map.addAttribute("question", question);
        Quiz quiz = question.getQuiz();
        List<QuestionOption> options = questionOptionService.findAllByQuestion(question);
        QuestionOption questionOption1 = options.get(0);
        QuestionOption questionOption2 = options.get(1);
        QuestionOption questionOption3 = options.get(2);
        QuestionOption questionOption4 = options.get(3);
        map.addAttribute("option1", questionOption1);
        map.addAttribute("option2", questionOption2);
        map.addAttribute("option3", questionOption3);
        map.addAttribute("option4", questionOption4);
//        map.addAttribute("options", options);
        map.addAttribute("quiz", quiz);

        return "editQuestion";

    }

    @PostMapping("/editQuestion")
    public String editQuestion(
            @ModelAttribute Question question, ModelMap map,
            @RequestParam("title1") String title1,
            @RequestParam("title2") String title2,
            @RequestParam("title3") String title3,
            @RequestParam("title4") String title4
//            @RequestParam("qOption") String[] optionId
    ) {
        questionService.save(question);
        List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(question);
        questionOptionService.editOptions(question, allByQuestion.get(0), title1);
        questionOptionService.editOptions(question, allByQuestion.get(1), title2);
        questionOptionService.editOptions(question, allByQuestion.get(2), title3);
        questionOptionService.editOptions(question, allByQuestion.get(3), title4);

        questionOptionService.changeOption(allByQuestion);


        int id = question.getQuiz().getId();
        map.addAttribute("quiz", id);

        return "redirect:/questions/byQuiz/" + id;


    }
}

