package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionAndOptionRequest {
    @NotEmpty(message = "title should be not empty")
    private String title;
    @PositiveOrZero
    private Double score;
    private QuestionType questionType;
    private int quizId;

    private List<SaveQuestionOptionRequest> options;

}
