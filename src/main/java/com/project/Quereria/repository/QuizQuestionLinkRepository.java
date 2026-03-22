package com.project.Quereria.repository;

import com.project.Quereria.entity.QuizQuestionLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionLinkRepository extends JpaRepository<QuizQuestionLink, Long> {
    List<QuizQuestionLink> findByQuizId(Long quizId);
}