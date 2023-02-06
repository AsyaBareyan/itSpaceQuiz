package com.example.itspacequizrest.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizrest.dto.SaveQuestionOptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;
    private final ModelMapper modelMapper;


    public List<QuestionOption> addAnswerOptions(List<Integer> options) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        for (int option : options) {
            QuestionOption byId = questionOptionRepository.getById(option);
            questionOptions.add(byId);
        }
        return questionOptions;
    }

    public void changeOption(Question question, SaveQuestionOptionRequest questionOption) {
        List<QuestionOption> allByQuestion = findAllByQuestion(question);
        for (QuestionOption option : allByQuestion) {
            QuestionOption updateOption = questionOptionRepository.getById(option.getId());
            updateOption.setQuestion(question);
            updateOption.setTitle(questionOption.getTitle());
            updateOption.setCorrect(questionOption.isCorrect());
            questionOptionRepository.save(updateOption);
            return;

        }
    }


    public void deleteById(int id) {
        questionOptionRepository.deleteById(id);
    }


    public List<QuestionOption> findAllByQuestion(Question question) {

        return questionOptionRepository.findAllByQuestion(question);
    }


    public void addOptions(Question question, List<SaveQuestionOptionRequest> options) {

        for (SaveQuestionOptionRequest option : options) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestion(question);
            questionOption.setTitle(option.getTitle());
            questionOption.setCorrect(option.isCorrect());
            questionOptionRepository.save(questionOption);
            log.info("Successfully saved option with id {}", questionOptionRepository.save(questionOption).getId());
        }
    }

}

