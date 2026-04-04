package com.project.Quereria.controller;

import com.project.Quereria.dto.request.QuizSubmitRequest;
import com.project.Quereria.dto.response.*;
import com.project.Quereria.dto.request.QuizRequest;
import com.project.Quereria.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Quereria.dto.response.ResultResponse;
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

    @GetMapping("/{id}/play")
    public ResponseEntity<QuizPlayResponse> getQuizForPlay(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizForPlay(id));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<QuizSubmitResponse> submitQuiz(
            @PathVariable Long id,
            @RequestBody QuizSubmitRequest request,
            HttpSession session
    ) {
        Object userIdObj = session.getAttribute("userId");
        Long currentUserId = userIdObj != null ? Long.valueOf(userIdObj.toString()) : null;
        return ResponseEntity.ok(quizService.submitQuiz(id, request, currentUserId));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<ResultResponse>> getQuizResults(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizResults(id));
    }
    @GetMapping("/history")
    public ResponseEntity<List<HistoryItemResponse>> getHistory(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        Long userId = userIdObj != null ? Long.valueOf(userIdObj.toString()) : null;
        return ResponseEntity.ok(quizService.getHistory(userId));
    }
}