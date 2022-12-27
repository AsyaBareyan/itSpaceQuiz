package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public Answer save(Answer answer) {


        return answerRepository.save(answer);


    }

    public Question saveAndReturn(Answer answer, Quiz quiz) {
        Answer saveAnswer = answerRepository.save(answer);
        List<Question> allQuestionsByQuiz = questionRepository.findAllByQuiz(quiz);
        List<Answer> allByUser = answerRepository.findAllByUser(saveAnswer.getUser());
        List<Answer> byQuiz = new ArrayList<>();
        for (Answer answer1 : allByUser) {
            if (answer1.getQuestion().getQuiz().getId() == quiz.getId()) {
                byQuiz.add(answer1);
            }
        }
        if (allQuestionsByQuiz.size() == byQuiz.size()) {

            return null;
        }

        for (Answer answer2 : byQuiz) {

            allQuestionsByQuiz.remove(answer2.getQuestion());

        }

        return allQuestionsByQuiz.get(0);
    }


    public double result(List<Answer> answers) {
        double result = 0;
        List<Answer> isTrue = checkIsTrue(answers);
        if (isTrue.size() == answers.size()) {

            for (Answer answer : answers) {

                if (answer.getQuestionOption().isCorrect()) {
                    isTrue.add(answer);

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


    public List<Answer> checkIsTrue(List<Answer> answers) {
        List<Answer> isTrue = new ArrayList<>();
        for (Answer answer : answers) {

            if (answer.getQuestionOption().isCorrect()) {
                isTrue.add(answer);

            }
        }
        return isTrue;
    }
}
