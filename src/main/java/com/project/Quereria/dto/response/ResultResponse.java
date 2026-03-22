package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResultResponse {
    private Long id;
    private Long quizId;
    private Integer score;
    private Long userId;
    private LocalDateTime createdAt;
}