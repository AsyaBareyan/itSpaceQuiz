package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizrest.dto.ResultDto;
import com.example.itspacequizrest.dto.SaveAnswerRequest;
import com.example.itspacequizrest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionOptionService questionOptionService;
    private final QuizService quizService;
    private final QuestionService questionService;


    public ResultDto createAnswer(int quizId, CurrentUser currentUser, SaveAnswerRequest saveAnswerRequest) {
        Quiz quiz = quizService.getById(quizId);
        Answer answer = new Answer();
        answer.setUser(currentUser.getUser());
        answer.setDateTime(LocalDateTime.now());
        Question question = questionRepository.getById(saveAnswerRequest.getQuestionId());
        answer.setQuestion(question);
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(saveAnswerRequest.getQuestionOption());
        answer.setQuestionOption(questionOptions);
        return saveAndReturn(answer, quiz);
    }

    public ResultDto saveAndReturn(Answer answer, Quiz quiz) {

        Answer saveAnswer = answerRepository.save(answer);
        log.info("Save answer: {}", saveAnswer);
        List<Question> allQuestionsByQuiz = questionRepository.findAllByQuiz(quiz);
        List<Answer> allByUser = answerRepository.findAllByUser(saveAnswer.getUser());
        List<Answer> allAnswersByQuiz = new ArrayList<>();
        log.info("Filtering answers by quiz with id: {}", quiz.getId());
        for (Answer allAnswersByUser : allByUser) {
            if (allAnswersByUser.getQuestion().getQuiz().getId() == quiz.getId()) {
                allAnswersByQuiz.add(allAnswersByUser);
            }
        }
        ResultDto resultDto = new ResultDto();
        if (allQuestionsByQuiz.size() == allAnswersByQuiz.size()) {
            log.info("Calculating result from all answers by quiz");
            resultDto.setResult(calculateScore(allAnswersByQuiz));
            log.info("Calculating max result from all questions by quiz");
            resultDto.setMaxResult(maxResult(allQuestionsByQuiz));
            log.info("Returning result dto with result: {} and max result: {}", calculateScore(allAnswersByQuiz), maxResult(allQuestionsByQuiz));
            return resultDto;

        }

        for (Answer alreadyAnswered : allAnswersByQuiz) {

            allQuestionsByQuiz.remove(alreadyAnswered.getQuestion());

        }
        resultDto.setQuestion(allQuestionsByQuiz.get(0));
        log.info("Returning result dto with next question: {}", allQuestionsByQuiz.get(0));
        return resultDto;

    }


    public double calculateScore(List<Answer> answers) {
        double result = 0;
        for (Answer answer : answers) {

            List<QuestionOption> allByQuestion = questionOptionRepository.findAllByQuestion(answer.getQuestion());
            List<QuestionOption> correctOptions = getCorrectOptions(allByQuestion);
            if (correctOptions.size() == answer.getQuestionOption().size())
                if (answer.getQuestionOption().containsAll(correctOptions)) {
                    result += answer.getQuestion().getScore();
                }
        }
        return result;
    }

    private List<QuestionOption> getCorrectOptions(List<QuestionOption> options) {
        return options.stream().filter(QuestionOption::isCorrect).collect(Collectors.toList());
    }

    public double maxResult(List<Question> questions) {
        double result = 0;
        for (Question question : questions) {

            double score = question.getScore();

            result = result + score;

        }
        return result;
    }


    public Answer answersByQuiz(int id) {
        List<Question> questions = questionService.findAllByQuiz(quizService.getById(id));
        for (Question question : questions) {
            return answerRepository.findByQuestion(question);
        }
        return null;
    }
}
