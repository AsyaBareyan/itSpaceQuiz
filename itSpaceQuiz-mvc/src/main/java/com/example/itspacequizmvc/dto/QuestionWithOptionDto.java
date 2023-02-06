package com.example.itspacequizmvc.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import com.example.itspacequizcommon.entity.Quiz;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithOptionDto {
    private int id;
    @NotEmpty(message = "title shoul be not empty")
    private String title;
    @PositiveOrZero
    private double score;
    @NotNull
    private QuestionType questionType;
    private Quiz quiz;
    @NotEmpty
    private List<String> options;
    private List<String> correctOptions;

}
