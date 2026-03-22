package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private Integer number;
    private String text;
    private String type;
    private Integer timerSeconds;
    private List<AnswerResponse> answers;
}