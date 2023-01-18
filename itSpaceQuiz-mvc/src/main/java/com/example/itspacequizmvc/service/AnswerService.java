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
        List<Question> remainingQuestions = new ArrayList<>(allQuestionsByQuiz);

        List<Answer> answers = answerRepository.findAllByUser(saveAnswer.getUser());
        List<Answer> allAnswersByQuiz = new ArrayList<>();
        for (Answer answer1 : answers) {
            if (answer1.getQuestion().getQuiz().getId() == quiz.getId()) {
                allAnswersByQuiz.add(answer1);
            }
        }
        if (allQuestionsByQuiz.size() == allAnswersByQuiz.size()) {

            return null;
        }

        for (Answer answer2 : allAnswersByQuiz) {

            remainingQuestions.remove(answer2.getQuestion());

        }

        return remainingQuestions.get(0);
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
}
