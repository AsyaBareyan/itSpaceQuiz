package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizcommon.repository.QuizRepository;
import com.example.itspacequizmvc.dto.OptionsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class QuestionOptionServiceTest {
    @Autowired
    private QuestionOptionService questionOptionService;
    @Autowired
    private QuestionOptionRepository questionOptionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Test
    void save_options_test() {
        List<String> options = Arrays.asList("Option 1", "Option 2", "Option 3");
        List<String> correctOption = Arrays.asList("Option 1");
        Map<String, Boolean> optionsMap = questionOptionService.saveOptions(options, correctOption);
        assertEquals(3, optionsMap.size());
        assertTrue(optionsMap.get("Option 1"));
        assertFalse(optionsMap.get("Option 2"));
        assertFalse(optionsMap.get("Option 3"));
    }

    @Test
    void add_options_test() {
        Question question = getQuestion();

        List<String> options = Arrays.asList("Paris", "Berlin", "London", "Yerevan");
        List<String> correctOptions = Arrays.asList("Yerevan");

        questionOptionService.addOptions(question, options, correctOptions);

        List<QuestionOption> savedOptions = questionOptionRepository.findAllByQuestion(question);
        assertEquals(4, savedOptions.size());

        for (QuestionOption savedOption : savedOptions) {
            if (savedOption.getTitle().equals("Yerevan")) {
                assertTrue(savedOption.isCorrect());
            } else {
                assertFalse(savedOption.isCorrect());
            }
        }
    }


    @Test
    @Transactional
    void addAnswerOptions() {
        getOption1();
        getOption2();
        List<Integer> options = Arrays.asList(getOption1().getId(), getOption2().getId());
        List<QuestionOption> questionOptions = questionOptionService.addAnswerOptions(options);

        assertThat(questionOptions).hasSize(2);
        assertThat(questionOptions.get(0).getTitle()).isEqualTo("Yerevan");
        assertThat(questionOptions.get(1).getTitle()).isEqualTo("Rome");

    }

    @Test
    @Transactional
    void chang_option_test() {
        Question question = getQuestion();
        QuestionOption option1 = getOption1();
        int[] optionId = {option1.getId()};
        String[] optionTitle = {"Yere1"};

        OptionsDto options = new OptionsDto();
        options.setOptionId(optionId);
        options.setOptionTitle(optionTitle);
        options.setCorrectOptions(Arrays.asList(option1.getId()));
        questionOptionService.changeOption(question, options);
        QuestionOption updatedOption = questionOptionRepository.getById(option1.getId());
        assertEquals("Yere1", updatedOption.getTitle());

    }

    @Test
    void delete_by_id_test() {
        assertTrue(questionOptionRepository.findById(getOption1().getId()).isPresent());
        questionOptionService.deleteById(getOption1().getId());
        assertFalse(questionOptionRepository.findById(1).isPresent());
    }

    @Test
    @Transactional
    void find_by_id_test() {
        QuestionOption option1 = getOption1();
        QuestionOption actualOption = questionOptionService.findById(option1.getId());
        QuestionOption expectedOption = questionOptionRepository.getById(option1.getId());
        assertEquals(expectedOption, actualOption);
    }

    private Quiz getQuiz() {
        Quiz quiz = Quiz.builder().id(1).title("Quiz 1").createdDateTime(LocalDateTime.now()).build();
        return quizRepository.save(quiz);
    }

    private Question getQuestion() {
        Question question1 = Question.builder()
                .id(1)
                .title("what is the capital of Armenia")
                .score(10.0)
                .questionType(QuestionType.SINGLE_SELECT)
                .quiz(getQuiz())
                .build();
        return questionRepository.save(question1);
    }

    private QuestionOption getOption1() {
        QuestionOption option1 = new QuestionOption();
        option1.setTitle("Yerevan");
        option1.setCorrect(true);
        option1.setQuestion(getQuestion());
        return questionOptionRepository.save(option1);

    }

    private QuestionOption getOption2() {
        QuestionOption option2 = new QuestionOption();
        option2.setTitle("Rome");
        option2.setCorrect(false);
        option2.setQuestion(getQuestion());
        return questionOptionRepository.save(option2);
    }

}