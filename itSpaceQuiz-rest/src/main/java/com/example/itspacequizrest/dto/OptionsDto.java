package com.example.itspacequizrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsDto {
    private int id;
    private String title;
    private boolean isCorrect;
}
