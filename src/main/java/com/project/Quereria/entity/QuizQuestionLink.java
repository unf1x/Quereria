package com.project.Quereria.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_question_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;
}
