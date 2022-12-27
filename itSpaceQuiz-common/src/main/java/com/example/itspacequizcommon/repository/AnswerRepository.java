package com.example.itspacequizcommon.repository;

import com.example.itspacequizcommon.entity.Answer;
import com.example.itspacequizcommon.entity.Question;
import com.example.itspacequizcommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer,Integer> {
List<Answer> findAllByUser(User user);








}
