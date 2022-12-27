package com.example.itspacequizcommon.repository;

import com.example.itspacequizcommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username);
}
