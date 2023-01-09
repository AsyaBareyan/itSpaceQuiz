package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizrest.dto.QuestionResponseDto;
import com.example.itspacequizrest.dto.SaveQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public List<QuestionResponseDto> findAll() {
        List<QuestionResponseDto> result = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            result.add(modelMapper.map(question, QuestionResponseDto.class));

        }
        return result;
    }

    public ResponseEntity deleteById(int id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    public QuestionResponseDto save(SaveQuestionRequest saveQuestionRequest) {
//        Question question = modelMapper.map(saveQuestionRequest, Question.class);
//
//        questionRepository.save(question);
//        return modelMapper.map(question, QuestionResponseDto.class);
//    }
        public Question save(Question question) {

       return questionRepository.save(question);

    }

    public ResponseEntity update(int id, SaveQuestionRequest saveQuestionRequest) {
        Optional<Question> question = questionRepository.findById(id);
        if (!question.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Question byId = question.get();
        if (saveQuestionRequest.getTitle() != null) {
            byId.setTitle(saveQuestionRequest.getTitle());
        }
        if (saveQuestionRequest.getScore() != null) {
            byId.setScore(saveQuestionRequest.getScore());
        }
        if (saveQuestionRequest.getQuestionType() != null) {
            byId.setQuestionType(saveQuestionRequest.getQuestionType());
        }
//            byId.getQuiz().setId(saveQuestionRequest.getQuizId());


        questionRepository.save(byId);
        QuestionResponseDto questionResponseDto = modelMapper.map(byId, QuestionResponseDto.class);
        return ResponseEntity.ok(questionResponseDto);
    }

    public Question findById(int id) {
        return questionRepository.getById(id);
    }

    public List<Question> findAllByQuiz(Quiz quiz) {

        return questionRepository.findAllByQuiz(quiz);
    }
}
