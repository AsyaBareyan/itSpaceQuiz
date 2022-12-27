package com.example.itspacequizcommon.repository;

import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByQuiz(Quiz quiz);


    Question findByQuiz(Quiz quiz);
}
