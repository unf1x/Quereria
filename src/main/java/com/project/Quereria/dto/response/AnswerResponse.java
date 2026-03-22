package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponse {
    private Long id;
    private String text;
    private Boolean correct;
}