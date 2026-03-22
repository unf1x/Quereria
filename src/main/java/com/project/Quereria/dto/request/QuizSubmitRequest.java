package com.project.Quereria.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequest {
    private List<SubmitAnswerItemRequest> answers;
}