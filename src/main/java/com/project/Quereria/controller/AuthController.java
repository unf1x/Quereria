package com.project.Quereria.controller;

import com.project.Quereria.dto.request.LoginRequest;
import com.project.Quereria.dto.request.RegisterRequest;
import com.project.Quereria.dto.response.AuthUserResponse;
import com.project.Quereria.dto.response.LoginResponse;
import com.project.Quereria.dto.response.RegisterResponse;
import com.project.Quereria.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request, HttpSession session) {
        return ResponseEntity.ok(authService.register(request, session));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        return ResponseEntity.ok(authService.login(request, session));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserResponse> me(HttpSession session) {
        return ResponseEntity.ok(authService.getCurrentUser(session));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.noContent().build();
    }
}