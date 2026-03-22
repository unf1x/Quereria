package com.project.Quereria.controller;

import com.project.Quereria.dto.QuizCreateResponse;
import com.project.Quereria.dto.QuizSummaryResponse;
import com.project.Quereria.entity.Quiz;
import com.project.Quereria.entity.QuizRequest;
import com.project.Quereria.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizCreateResponse> createQuiz(@RequestBody QuizRequest request) {
        Long quizId = quizService.createQuiz(request);
        return ResponseEntity.ok(new QuizCreateResponse(quizId, "Квиз успешно создан"));
    }

    @GetMapping
    public ResponseEntity<List<QuizSummaryResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }
}