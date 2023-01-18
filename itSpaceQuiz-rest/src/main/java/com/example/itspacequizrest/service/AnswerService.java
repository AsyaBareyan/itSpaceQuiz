package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizrest.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;


    public ResultDto saveAndReturn(Answer answer, Quiz quiz) {

        Answer saveAnswer = answerRepository.save(answer);
        List<Question> allQuestionsByQuiz = questionRepository.findAllByQuiz(quiz);
        List<Answer> allByUser = answerRepository.findAllByUser(saveAnswer.getUser());
        List<Answer> byQuiz = new ArrayList<>();
        for (Answer answer1 : allByUser) {
            if (answer1.getQuestion().getQuiz().getId() == quiz.getId()) {
                byQuiz.add(answer1);
            }
        }
        ResultDto resultDto = new ResultDto();
        if (allQuestionsByQuiz.size() == byQuiz.size()) {
            resultDto.setResult(result(byQuiz));
            resultDto.setMaxResult(maxResult(allQuestionsByQuiz));
            return resultDto;

        }

        for (Answer answer2 : byQuiz) {

            allQuestionsByQuiz.remove(answer2.getQuestion());

        }
        resultDto.setQuestion(allQuestionsByQuiz.get(0));
        return resultDto;
//        return allQuestionsByQuiz.get(0);
    }

    public double result(List<Answer> answers) {
        double result = 0;
        for (Answer answer : answers) {

            List<QuestionOption> allByQuestion = questionOptionRepository.findAllByQuestion(answer.getQuestion());
            List<QuestionOption> questionOptions = checkIsTrueOptions(allByQuestion);
            if (questionOptions.size() == answer.getQuestionOption().size()) {
                for (QuestionOption questionOption : answer.getQuestionOption()) {
                    if (questionOption.isCorrect()) {
                        double score = answer.getQuestion().getScore();

                        result = result + score;
                    }
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


    public Answer findByQuestion(Question question) {
        return answerRepository.findByQuestion(question);
    }
}
