package com.project.Quereria.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuizRequest {
    private String title;
    private String description;
    private List<QuestionRequest> questions;
}
