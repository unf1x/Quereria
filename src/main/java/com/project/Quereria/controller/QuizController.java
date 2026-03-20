package com.project.Quereria.controller;

import com.project.Quereria.entity.QuizRequest;
import com.project.Quereria.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<Long> createQuiz(@RequestBody QuizRequest request) {
        Long quizId = quizService.createQuiz(request);
        return ResponseEntity.ok(quizId);
    }
}
