package com.project.Quereria.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_answer_bond")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerBond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "is_corrected", nullable = false)
    private Boolean isCorrected;
}