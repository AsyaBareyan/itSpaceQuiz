package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionOptionRequest {
    private String[] titles;
    private boolean isCorrect;
    private int questionId;

}
