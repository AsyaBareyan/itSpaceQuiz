package com.example.itspacequizcommon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question",schema = "public")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double score;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;
    @ManyToOne
    private Quiz quiz;
}
