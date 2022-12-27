package com.example.itspacequizmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.itspacequizmvc","com.example.itspacequizcommon"})
@EnableJpaRepositories("com.example.itspacequizcommon")
@EntityScan("com.example.itspacequizcommon")
public class ItSpaceQuizMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItSpaceQuizMvcApplication.class, args);
    }


}
