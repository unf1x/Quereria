package com.project.Quereria.repository;

import com.project.Quereria.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByQuizIdOrderByCreatedAtDesc(Long quizId);
    List<Result> findAllByOrderByCreatedAtDesc();
    List<Result> findByUserIdOrderByCreatedAtDesc(Long userId);
}