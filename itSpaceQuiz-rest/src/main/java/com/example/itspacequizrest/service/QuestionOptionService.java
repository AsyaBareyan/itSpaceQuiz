package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import com.example.itspacequizrest.dto.SaveQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;


    public QuestionOption save(QuestionOption questionOption) {
        return questionOptionRepository.save(questionOption);
    }


    public List<QuestionOption> addAnswerOptions(List<Integer> options) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        for (int option : options) {
            QuestionOption byId = questionOptionRepository.getById(option);
            questionOptions.add(byId);
        }
        return questionOptions;
    }

    public QuestionOption changeOption(List<QuestionOption> questionOptions) {
        for (QuestionOption questionOption : questionOptions) {
            QuestionOption byId = questionOptionRepository.getById(questionOption.getId());
            return questionOptionRepository.save(byId);
        }
        return null;
    }

    public void saveOptions(Question question, String title) {
        QuestionOption questionOption = new QuestionOption();

        String[] split = title.split(",");
        if (question.getQuestionType() == QuestionType.SINGLE_SELECT) {
            questionOption.setTitle(split[0]);
        } else {
            questionOption.setTitle(split[1]);
        }
        questionOption.setQuestion(question);
        if (Arrays.asList(split).contains("on")) {
            questionOption.setCorrect(true);
        }

        questionOptionRepository.save(questionOption);

    }

    public void editOptions(Question question, QuestionOption questionOption, String title) {

        String[] split = title.split(",");
        if (question.getQuestionType() == QuestionType.SINGLE_SELECT) {
            questionOption.setTitle(split[0]);
        } else {
            questionOption.setTitle(split[1]);
        }
        questionOption.setQuestion(question);
        if (!Arrays.asList(split).contains("on")) {
            questionOption.setCorrect(false);
        }
        if (Arrays.asList(split).contains("on")) {
            questionOption.setCorrect(true);
        }
        questionOptionRepository.save(questionOption);

    }

    public void deleteById(int id) {
        questionOptionRepository.deleteById(id);
    }


    public QuestionOption findById(int id) {
        return questionOptionRepository.getById(id);
    }

    public List<QuestionOption> findAllByQuestion(Question question) {

        return questionOptionRepository.findAllByQuestion(question);
    }


//    public List<QuestionOption> addOption()
}
