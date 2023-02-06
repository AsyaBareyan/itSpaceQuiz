package com.example.itspacequizmvc.dto;

import com.example.itspacequizcommon.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private Question question;
    private double result;
    private double maxResult;


}
