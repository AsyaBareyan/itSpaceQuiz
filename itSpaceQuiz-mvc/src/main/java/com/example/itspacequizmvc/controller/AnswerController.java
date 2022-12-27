package com.example.itspacequizmvc.controller;

import com.example.itspacequizcommon.entity.*;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizmvc.service.AnswerService;
import com.example.itspacequizmvc.service.QuestionOptionService;
import com.example.itspacequizmvc.service.QuestionService;
import com.example.itspacequizmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;


    @PostMapping("/user/quiz/{id}")
    public String answer(@PathVariable("id") int id,
                         @RequestParam("qOption") String[] titles, ModelMap map) {
        Quiz quiz = quizService.findById(id);
        Answer answer = new Answer();

        answer.setDateTime(LocalDateTime.now());
        User user = new User(2, "poxos", "poxosyan",
                "poxosyan@mail.ru", "poxos", UserType.valueOf("STUDENT"));
        answer.setUser(user);

        QuestionOption update = questionOptionService.update(titles);
        answer.setQuestionOption(update);
        Question question = update.getQuestion();
        answer.setQuestion(question);
        Question saveAndReturn = answerService.saveAndReturn(answer, quiz);
        List<Question> allQuestionsByQuiz = questionService.findAllByQuiz(quiz);
        List<Answer> allByUser = answerRepository.findAllByUser(user);
        List<Answer> byQuiz = new ArrayList<>();
        for (Answer answer1 : allByUser) {
            if (answer1.getQuestion().getQuiz().getId() == quiz.getId()) {
                byQuiz.add(answer1);
            }
        }
        if (saveAndReturn == null) {
            map.addAttribute("result", answerService.result(byQuiz));
            map.addAttribute("maxResult",answerService.maxResult(allQuestionsByQuiz));
            return "result";
        }
        List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(saveAndReturn);

//        List<Question> questions = answerService.saveAndReturn(answer, quiz);
//        map.addAttribute("questions",questions);

        map.addAttribute("question", saveAndReturn);
        map.addAttribute("quiz", quiz);
        map.addAttribute("options", allByQuestion);

//        answerService.save(answer);

        return "answer";
    }




}
