package com.example.itspacequizrest.controller;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizrest.dto.*;
import com.example.itspacequizrest.service.QuestionOptionService;
import com.example.itspacequizrest.service.QuestionService;
import com.example.itspacequizrest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionOptionService questionOptionService;
    private final ModelMapper modelMapper;


    @GetMapping("/question")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
        List<QuestionResponseDto> all = questionService.findAll();
        return ResponseEntity.ok(all);
    }


    @GetMapping("/question/quiz/{id}")
    public ResponseEntity<QuestionOptionResponseDto> responseAllQuestionsByQuiz(@PathVariable("id") int id) {
        QuestionOptionResponseDto questionOptionResponseDto = new QuestionOptionResponseDto();
        List<Question> allByQuiz = questionService.findAllByQuiz(quizService.getById(id));

        for (Question question : allByQuiz) {
            questionOptionResponseDto.setQuestion(question);
            List<OptionsDto> optionsDtos = new ArrayList<>();
            List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(question);
            for (QuestionOption questionOption : allByQuestion) {
                optionsDtos.add(modelMapper.map(questionOption, OptionsDto.class));
            }

            questionOptionResponseDto.setOptions(optionsDtos);
            return ResponseEntity.ok(questionOptionResponseDto);
        }

        return ResponseEntity.noContent().build();
    }


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
                                       @RequestBody SaveQuestionAndOptionRequest saveQuestionAndOptionRequest) {
        questionService.update(id, saveQuestionAndOptionRequest);
        Question question = modelMapper.map(saveQuestionAndOptionRequest, Question.class);
        List<QuestionOption> allByQuestion = questionOptionService.findAllByQuestion(question);
        questionOptionService.editOptions(question, allByQuestion.get(0), saveQuestionAndOptionRequest.getTitle1());
        questionOptionService.editOptions(question, allByQuestion.get(1), saveQuestionAndOptionRequest.getTitle2());
        questionOptionService.editOptions(question, allByQuestion.get(2), saveQuestionAndOptionRequest.getTitle3());
        questionOptionService.editOptions(question, allByQuestion.get(3), saveQuestionAndOptionRequest.getTitle4());

        questionOptionService.changeOption(allByQuestion);

        return ResponseEntity.ok().build();
    }
}

