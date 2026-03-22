package com.project.Quereria.controller;

import com.project.Quereria.dto.response.QuizCreateResponse;
import com.project.Quereria.dto.response.QuizFullResponse;
import com.project.Quereria.dto.response.QuizSummaryResponse;
import com.project.Quereria.dto.request.QuizRequest;
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

    @GetMapping("/{id}/full")
    public ResponseEntity<QuizFullResponse> getQuizFull(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizFullById(id));
    }
}