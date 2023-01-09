package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizrest.dto.QuestionResponseDto;
import com.example.itspacequizrest.dto.SaveQuestionAndOptionRequest;
import com.example.itspacequizrest.dto.SaveQuestionRequest;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;
    private final ModelMapper modelMapper;


    @GetMapping("/question")
    public List<QuestionResponseDto> getAllQuestions() {

        return questionService.findAll();
    }

//    @PostMapping("/question")
//    public QuestionResponseDto createQuestion(@RequestBody SaveQuestionRequest saveQuestionRequest) {
//        return questionService.save(saveQuestionRequest);
//    }
//    @PostMapping("/question")
//    public @ResponseBody ResponseEntity<QuestionResponseDto>  createQuestion(
//            @RequestBody SaveQuestionRequest saveQuestionRequest,
//            @RequestParam("title1") String title1,
//            @RequestParam("title2") String title2,
//            @RequestParam("title3") String title3,
//            @RequestParam("title4") String title4) {
//        questionService.save(saveQuestionRequest);
//        Question question = modelMapper.map(saveQuestionRequest, Question.class);
//
//        questionOptionService.saveOptions(question, title1);
//        questionOptionService.saveOptions(question, title2);
//        questionOptionService.saveOptions(question, title3);
//        questionOptionService.saveOptions(question, title4);

//        HttpHeaders resp=new HttpHeaders();
//        resp.set()
//        int id = question.getQuiz().getId();
//        map.addAttribute("quiz", id);

//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/question")
    public @ResponseBody ResponseEntity<QuestionResponseDto> createQuestion(
            @RequestBody SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        Question question = new Question();
        question.setTitle(saveQuestionAndOptionRequest.getTitle());
        question.setScore(saveQuestionAndOptionRequest.getScore());
        question.setQuestionType(saveQuestionAndOptionRequest.getQuestionType());
        question.setQuiz(quizService.getById(saveQuestionAndOptionRequest.getQuizId()));
        questionService.save(question);
        questionOptionService.saveOptions(question, saveQuestionAndOptionRequest.getTitle1());
        questionOptionService.saveOptions(question, saveQuestionAndOptionRequest.getTitle2());
        questionOptionService.saveOptions(question, saveQuestionAndOptionRequest.getTitle3());
        questionOptionService.saveOptions(question, saveQuestionAndOptionRequest.getTitle4());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int id) {

        return questionService.deleteById(id);


    }

    @PutMapping("/question/{id}")
    public ResponseEntity editQuestion(@PathVariable("id") int id,
                                       @RequestBody SaveQuestionRequest saveQuestionRequest) {
        return questionService.update(id, saveQuestionRequest);


    }
}

