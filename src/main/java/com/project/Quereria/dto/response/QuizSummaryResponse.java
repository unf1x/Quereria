package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSummaryResponse {
    private Long id;
    private String title;
    private String description;
    private Integer questionCount;
}