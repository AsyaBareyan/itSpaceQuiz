package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizmvc.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionOptionService questionOptionService;


    public Answer save(Answer answer) {
        Answer savedAnswer = answerRepository.save(answer);
        log.info("Successfully saved answer with id {}", savedAnswer.getId());
        return savedAnswer;
    }

    public ResultDto saveAndReturn(Answer answer, Quiz quiz) {
        ResultDto resultDto = new ResultDto();
        log.info("Saving answer: {}", answer);
        Answer saveAnswer = answerRepository.save(answer);
        List<Question> allQuestionsByQuiz = questionRepository.findAllByQuiz(quiz);
        List<Answer> answers = answerRepository.findAllByUser(saveAnswer.getUser());
        List<Answer> allAnswersByQuiz = new ArrayList<>();
        log.info("Filtering answers by quiz with id: {}", quiz.getId());
        for (Answer answerByUser : answers) {
            if (answerByUser.getQuestion().getQuiz().getId() == quiz.getId()) {
                allAnswersByQuiz.add(answerByUser);
            }
        }
        if (allQuestionsByQuiz.size() == allAnswersByQuiz.size()) {
            log.info("Calculating result from all answers by quiz");
            double result = result(allAnswersByQuiz);
            log.info("Calculating max result from all questions by quiz");
            double maxResult = maxResult(allQuestionsByQuiz);
            resultDto.setResult(result);
            resultDto.setMaxResult(maxResult);
            log.info("Returning result dto with result: {} and max result: {}", result, maxResult);
            return resultDto;
        }
        for (Answer alreadyAnswered : allAnswersByQuiz) {
            allQuestionsByQuiz.remove(alreadyAnswered.getQuestion());
        }
        resultDto.setQuestion(allQuestionsByQuiz.get(0));
        log.info("Returning result dto with next question: {}", allQuestionsByQuiz.get(0));
        return resultDto;
    }


    public double result(List<Answer> answers) {
        double result = 0;
        for (Answer answer : answers) {
            List<QuestionOption> allByQuestion = questionOptionRepository.findAllByQuestion(answer.getQuestion());
            List<QuestionOption> questionOptions = checkIsTrueOptions(allByQuestion);
            if (questionOptions.size() == answer.getQuestionOption().size()) {
                int isTrue = 0;
                for (QuestionOption questionOption : answer.getQuestionOption()) {
                    if (questionOption.isCorrect()) {
                        isTrue++;
                    }
                }
                if (questionOptions.size() == isTrue) {
                    double score = answer.getQuestion().getScore();
                    result = result + score;
                }
            }

        }
        return result;
    }


    public double maxResult(List<Question> questions) {
        double result = 0;
        for (Question question : questions) {

            double score = question.getScore();

            result = result + score;

        }
        return result;
    }


    public List<QuestionOption> checkIsTrueOptions(List<QuestionOption> questionOptions) {
        List<QuestionOption> isTrue = new ArrayList<>();
        for (QuestionOption questionOption : questionOptions) {
            if (questionOption.isCorrect()) {
                isTrue.add(questionOption);

            }
        }
        return isTrue;
    }

    public ResultDto addAnswer(Quiz quiz, Answer answer, List<Integer> options) {
        ResultDto resultDto = new ResultDto();
        answer.setDateTime(LocalDateTime.now());
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(options);
        answer.setQuestionOption(questionOptions);
        ResultDto saveAndReturn = saveAndReturn(answer, quiz);
        if (saveAndReturn.getQuestion() == null) {
            return saveAndReturn;
        }

        resultDto.setQuestion(saveAndReturn.getQuestion());
        return resultDto;
    }
}
