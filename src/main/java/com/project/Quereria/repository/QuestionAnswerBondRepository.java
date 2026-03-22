package com.project.Quereria.repository;

import com.project.Quereria.entity.QuestionAnswerBond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerBondRepository extends JpaRepository<QuestionAnswerBond, Long> {
    List<QuestionAnswerBond> findByQuestionId(Long questionId);
    List<QuestionAnswerBond> findByQuestionIdIn(List<Long> questionIds);
}