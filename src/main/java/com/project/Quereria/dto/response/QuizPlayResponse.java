package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizPlayResponse {
    private Long id;
    private String title;
    private String description;
    private List<PlayQuestionResponse> questions;
}