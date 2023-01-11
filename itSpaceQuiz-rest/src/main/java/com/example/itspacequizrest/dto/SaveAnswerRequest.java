package com.example.itspacequizrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAnswerRequest {
    private int userId;
    private int questionId;
    private List<Integer> questionOption;


}
