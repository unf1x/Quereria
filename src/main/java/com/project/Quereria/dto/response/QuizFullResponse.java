package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class QuizFullResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<QuestionResponse> questions;
}