package com.example.itspacequizcommon.repository;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.QuestionOption;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption,Integer> {

    List<QuestionOption> findAllByQuestion(Question question);

    QuestionOption findQuestionOptionByTitle(String title);

//    List<QuestionOption> findAllByCorrectIsTrue();
//    List<QuestionOption> findAllByQuestionAndCorrectIsTrue(Question question);

}
