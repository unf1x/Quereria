package com.project.Quereria.dto.request;

import lombok.Data;

@Data
public class SubmitAnswerItemRequest {
    private Long questionId;
    private Long selectedAnswerId;
    private String textAnswer;
}