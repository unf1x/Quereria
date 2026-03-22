package com.project.Quereria.repository;

import com.project.Quereria.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByNumber(Integer number);
    List<Question> findByIdIn(List<Long> ids);
}