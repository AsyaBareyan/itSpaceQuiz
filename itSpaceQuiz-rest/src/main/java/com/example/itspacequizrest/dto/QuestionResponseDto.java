package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {
    private int id;
    private String title;
    private Double score;
    private QuestionType questionType;
//    private List<OptionsDto> questionOptions;


}
