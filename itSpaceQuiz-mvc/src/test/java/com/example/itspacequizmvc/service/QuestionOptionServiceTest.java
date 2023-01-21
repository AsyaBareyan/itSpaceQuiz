package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class QuestionOptionServiceTest {

    @Mock
    private QuestionOptionRepository questionOptionRepository;

    @InjectMocks
    private QuestionOptionService questionOptionService;

    private QuestionOption option1, option2;
    private Question question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        question = new Question();
        question.setQuestionType(QuestionType.SINGLE_SELECT);
        option1 = new QuestionOption();
        option1.setTitle("Option 1");
        option1.setCorrect(true);
        option1.setQuestion(question);
        option2 = new QuestionOption();
        option2.setTitle("Option 2");
        option2.setCorrect(false);
        option2.setQuestion(question);
    }


    @Test
    void testAddAnswerOptions() {
        List<Integer> options = Arrays.asList(1, 2, 3);
        when(questionOptionRepository.getById(1)).thenReturn(option1);
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(options);
        assertTrue(questionOptions.contains(option1));
    }

    @Test
    void testChangeOption() {
        List<QuestionOption> options = Arrays.asList(option1);
        when(questionOptionRepository.getById(option1.getId())).thenReturn(option1);
        when(questionOptionRepository.save(option1)).thenReturn(option1);
        QuestionOption changedOption = questionOptionService.changeOption(options);
        assertEquals(changedOption, option1);
    }

    @Test
    void testSaveOptions() {
        questionOptionService.saveOptions(question, "Option 1,on");
        verify(questionOptionRepository, times(1)).save(any(QuestionOption.class));
    }

    @Test
    void testFindAllByQuestion() {
        when(questionOptionRepository.findAllByQuestion(question)).thenReturn(Arrays.asList(option1, option2));
        List<QuestionOption> options = questionOptionService.findAllByQuestion(question);
        assertTrue(options.contains(option1));
        assertTrue(options.contains(option2));
    }
}