package com.example.itspacequizrest.dto;

import com.example.itspacequizcommon.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithOptionDto {
  private Question question;
  private List<OptionsDto> options;
}
