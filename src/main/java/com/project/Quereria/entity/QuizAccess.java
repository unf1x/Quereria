package com.project.Quereria.entity;



import com.project.Quereria.entity.enums.AccessRights;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessRights rights;
}
