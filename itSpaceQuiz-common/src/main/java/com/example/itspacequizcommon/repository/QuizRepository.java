package com.example.itspacequizcommon.repository;

import com.example.itspacequizcommon.entity.Quiz;
import com.example.itspacequizcommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {

}
