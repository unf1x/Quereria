package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HistoryItemResponse {
    private Long resultId;
    private Long quizId;
    private String quizTitle;
    private Integer score;
    private LocalDateTime createdAt;
}