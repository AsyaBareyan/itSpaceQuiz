package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionRequest {
    @NotEmpty(message = "title should be not empty")
    private String title;
    @PositiveOrZero
    private Double score;
    private QuestionType questionType;
    private int quizId;


}
