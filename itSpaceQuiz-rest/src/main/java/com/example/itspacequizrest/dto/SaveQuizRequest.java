package com.example.itspacequizrest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuizRequest {
    @NotEmpty(message = "title should be not empty")
    private String title;


}
