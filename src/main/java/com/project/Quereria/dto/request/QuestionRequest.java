package com.project.Quereria.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String text;
    private Boolean open;
    private String openAnswer;

    private Boolean hasAnswers;
    private List<String> answers;
    private Integer correctIndex;

    private Boolean hasTimer;
    private TimeRequest time;

}
