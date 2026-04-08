package com.project.Quereria.service;

import com.project.Quereria.dto.request.LoginRequest;
import com.project.Quereria.dto.request.RegisterRequest;
import com.project.Quereria.dto.response.AuthUserResponse;
import com.project.Quereria.dto.response.LoginResponse;
import com.project.Quereria.dto.response.RegisterResponse;
import com.project.Quereria.entity.User;
import com.project.Quereria.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String SESSION_USER_ID = "userId";

    private final UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request, HttpSession session) {
        if (request == null) {
            throw new IllegalArgumentException("Тело запроса отсутствует");
        }

        if (isBlank(request.getName()) || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Имя, email и пароль обязательны");
        }

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = userRepository.save(
                User.builder()
                        .name(request.getName().trim())
                        .email(email)
                        .password(request.getPassword().trim())
                        .build()
        );

        session.setAttribute(SESSION_USER_ID, user.getId());

        return new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "Пользователь успешно зарегистрирован"
        );
    }

    public LoginResponse login(LoginRequest request, HttpSession session) {
        if (request == null) {
            throw new IllegalArgumentException("Тело запроса отсутствует");
        }

        if (isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Email и пароль обязательны");
        }

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        String password = request.getPassword().trim();

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        session.setAttribute(SESSION_USER_ID, user.getId());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "Вход выполнен успешно"
        );
    }

    public AuthUserResponse getCurrentUser(HttpSession session) {
        Object userIdObj = session.getAttribute(SESSION_USER_ID);

        if (userIdObj == null) {
            throw new IllegalArgumentException("Пользователь не авторизован");
        }

        Long userId = Long.valueOf(userIdObj.toString());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        return new AuthUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}