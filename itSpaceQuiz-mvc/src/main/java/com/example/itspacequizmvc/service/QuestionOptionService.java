package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;
import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.exception.NotFoundException;
import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizmvc.dto.OptionsDto;
import com.example.itspacequizmvc.dto.QuestionWithOptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;


    public Map<String, Boolean> saveOptions(List<String> options, List<String> correctOption) {
        Map<String, Boolean> optionsMap = new HashMap<>();
        for (String option : options) {
            if (correctOption != null && !correctOption.isEmpty() && correctOption.contains(option)) {
                optionsMap.put(option, true);
                log.debug("Option: {} is marked as correct.", option);
            } else {
                optionsMap.put(option, false);
                log.debug("Option: {} is marked as incorrect.", option);
            }
        }
        return optionsMap;
    }

    public void addOptions(Question question, QuestionWithOptionDto questionWithOptionDto) {
        Map<String, Boolean> saveOptions = saveOptions(questionWithOptionDto.getOptions(), questionWithOptionDto.getCorrectOptions());
        for (Map.Entry<String, Boolean> options : saveOptions.entrySet()) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestion(question);
            questionOption.setTitle(options.getKey());
            questionOption.setCorrect(options.getValue());
            questionOptionRepository.save(questionOption);
        }

    }

    public void addOptions(Question question, List<String> options, List<String> correctOptions) {
        Map<String, Boolean> saveOptions = saveOptions(options, correctOptions);
        for (Map.Entry<String, Boolean> option : saveOptions.entrySet()) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestion(question);
            questionOption.setTitle(option.getKey());
            questionOption.setCorrect(option.getValue());
            QuestionOption savedOption = questionOptionRepository.save(questionOption);
            log.info("Successfully saved option {}", savedOption);
        }
    }

    public List<QuestionOption> addAnswerOptions(List<Integer> options) {

        List<QuestionOption> questionOptions = new ArrayList<>();
        for (int option : options) {
            QuestionOption byId = questionOptionRepository.getById(option);
            questionOptions.add(byId);
            log.info("Successfully find option with id {} and added in {} list for answers", byId.getId(), questionOptions);
        }
        return questionOptions;
    }

    public void changeOption(Question question, OptionsDto options) {
        int[] optionId = question.getQuestionType() == QuestionType.SINGLE_SELECT ? options.getOptionId() : options.getMultiOptionId();
        String[] optionTitle = question.getQuestionType() == QuestionType.SINGLE_SELECT ? options.getOptionTitle() : options.getMultiOptionTitle();

            for (int i = 0; i < optionId.length; i++) {
                if (questionOptionRepository.existsById(optionId[i])) {
                    QuestionOption byId = questionOptionRepository.getById(optionId[i]);
                    log.info("Option found with id {}", byId.getId());
                    byId.setTitle(optionTitle[i]);
                    if (options.getCorrectOptions() != null && !options.getCorrectOptions().isEmpty() && options.getCorrectOptions().contains(optionId[i])) {
                        byId.setCorrect(true);
                    } else {
                        byId.setCorrect(false);
                    }
                    byId.setQuestion(question);
                    questionOptionRepository.save(byId);
                    log.info("Successfully saved option with id {}", questionOptionRepository.save(byId));
                }
            }
        if (options.getNewOptions() != null && !options.getNewOptions().isEmpty()) {
            addOptions(question, options.getNewOptions(), options.getCorrect());
        }
    }


    public List<QuestionOption> findAllByQuestion(Question question) {

        return questionOptionRepository.findAllByQuestion(question);
    }


    public void deleteById(int id) {
        log.info("Attempting to find option with id {}", id);
        if (questionOptionRepository.findById(id).isEmpty()) {
            log.error("Option not found for id {}", id);
            throw new NotFoundException("Option with id " + id + " not found.");
        }
        questionOptionRepository.deleteById(id);
        log.info("Option with id {} deleted", id);
    }

    public QuestionOption findById(int id) {
        if (!questionOptionRepository.existsById(id)) {
            log.error("Option not found for id {}", id);
            throw new NotFoundException("Option not found for provided id.");
        }
        QuestionOption option = questionOptionRepository.getById(id);
        log.info("Successfully found option with id {}", id);
        return option;
    }


}
