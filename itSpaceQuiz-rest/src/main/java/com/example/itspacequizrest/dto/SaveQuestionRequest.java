package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionRequest {
    private String title;
    private Double score;
    private QuestionType questionType;
    private int quizId;//karox em ardyoq urish dzev quizid-n vercnel?

}
