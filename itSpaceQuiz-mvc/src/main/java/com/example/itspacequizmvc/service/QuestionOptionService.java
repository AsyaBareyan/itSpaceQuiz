package com.example.itspacequizmvc.service;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;

import com.example.itspacequizcommon.repository.QuestionOptionRepository;
import com.example.itspacequizcommon.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;

    public QuestionOption save(QuestionOption questionOption) {
        return questionOptionRepository.save(questionOption);
    }



    public QuestionOption update(String[] titles) {
        for (String title : titles) {

            QuestionOption questionOptionByTitle = questionOptionRepository.findQuestionOptionByTitle(title);
            return questionOptionRepository.save(questionOptionByTitle);

        }
        return null;
    }

    public void saveOptions(Question question, String title) {
        QuestionOption questionOption = new QuestionOption();

        String[] split = title.split(",");
        questionOption.setTitle(split[0]);
        questionOption.setQuestion(question);
        if (split.length > 1) {
            questionOption.setCorrect(true);
        }
        questionOptionRepository.save(questionOption);

    }


    public void saveMultiOptions(Question question, String title) {
        QuestionOption questionOption = new QuestionOption();

        String[] split = title.split(",");
        questionOption.setTitle(split[1]);
        questionOption.setQuestion(question);
        if (split.length > 2) {
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
