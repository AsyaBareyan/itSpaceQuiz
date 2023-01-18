package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.*;
import com.example.itspacequizcommon.repository.AnswerRepository;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {
    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionOptionRepository questionOptionRepository;

    private AnswerService answerService;

    private Quiz quiz;
    private User user;
    private Question question1, question2;
    private QuestionOption option1, option2, option3, option4;
    private Answer answer1, answer2;
    private List<Answer> answers;
    private List<Question> questions;
    private List<QuestionOption> options;
    private List<QuestionOption> options1;
    private List<QuestionOption> options2;

    @BeforeEach
    public void setUp() {
        answerService = new AnswerService(answerRepository, questionRepository, questionOptionRepository);

        quiz = new Quiz();
        user=new User(1,"poxos","poxosyan","pox@mail.ru","pox",UserType.STUDENT);

        question1 = new Question();
        question2 = new Question();
        option1 = new QuestionOption();
        option2 = new QuestionOption();
        option3 = new QuestionOption();
        option4 = new QuestionOption();
        answer1 = new Answer();
        answer2 = new Answer();

        questions = Arrays.asList(question1, question2);
        options = Arrays.asList(option1, option2, option3, option4);

        question1.setQuiz(quiz);
        question1.setScore(5);
        question2.setQuiz(quiz);
        question2.setScore(10);

        option1.setQuestion(question1);
        option2.setQuestion(question1);
        option3.setQuestion(question2);
        option4.setQuestion(question2);
        option1.setCorrect(true);
        option2.setCorrect(false);
        option3.setCorrect(true);
        option4.setCorrect(false);

        options1 = Arrays.asList(option1, option2);
        options2 = Arrays.asList(option3,option4);

        answer1.setQuestion(question1);
        answer1.setQuestionOption(Arrays.asList(option1));
        answer1.setUser(user);
        answer2.setQuestion(question2);
        answer2.setQuestionOption(Arrays.asList(option3));
        answer2.setUser(user);
        answers = Arrays.asList(answer1, answer2);
    }

    @Test
    public void testSave() {
        when(answerRepository.save(answer1)).thenReturn(answer1);
        assertEquals(answer1, answerService.save(answer1));
    }

    @Test
    public void testSaveAndReturn_allQuestionsAnswered() {
        when(answerRepository.save(answer1)).thenReturn(answer1);
        when(questionRepository.findAllByQuiz(quiz)).thenReturn(questions);
        when(answerRepository.findAllByUser(user)).thenReturn(answers);

        assertNull(answerService.saveAndReturn(answer1, quiz));
    }

    @Test
    public void testSaveAndReturn_nextQuestion() {
        when(answerRepository.save(answer1)).thenReturn(answer1);
        when(questionRepository.findAllByQuiz(quiz)).thenReturn(questions);
        when(answerRepository.findAllByUser(user)).thenReturn(Arrays.asList(answer1));

        assertEquals(question2, answerService.saveAndReturn(answer1, quiz));
    }

@Test
    public void testResult(){

    when(questionOptionRepository.findAllByQuestion(question1)).thenReturn(options1);
    when(questionOptionRepository.findAllByQuestion(question2)).thenReturn(options2);


    double result = answerService.result(answers);
    assertEquals(15, result);
}

}