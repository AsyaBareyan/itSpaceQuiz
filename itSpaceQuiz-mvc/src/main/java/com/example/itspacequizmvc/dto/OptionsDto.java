package com.example.itspacequizmvc.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsDto {
    private int[] optionId;
    private int[] multiOptionId;

    @NotEmpty(message = "title should be not empty")
    private String[] optionTitle;

    @NotEmpty(message = "title should be not null")
    private String[] multiOptionTitle;
    private List<Integer> correctOptions;

    private List<String> newOptions;
    private List<String> correct;
}