package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizSubmitResponse {
    private Long quizId;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Integer scorePercent;
    private List<QuestionResultResponse> results;
}