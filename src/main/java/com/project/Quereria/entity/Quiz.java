package com.project.Quereria.entity;


import com.project.Quereria.entity.enums.QuizType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private QuizType type;

    private String cover;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDateTime dateOfCreation;
}
