package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResultResponse {
    private Long questionId;
    private Integer questionNumber;
    private String questionText;
    private boolean correct;
    private String correctAnswerText;
    private String userAnswerText;
}