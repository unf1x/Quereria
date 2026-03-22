package com.project.Quereria.entity;


import com.project.Quereria.entity.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(name = "timer_seconds")
    private Integer timerSeconds;

    @Column(name = "text")
    private String text;
}
