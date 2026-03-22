package com.project.Quereria.service;

import com.project.Quereria.dto.request.RegisterRequest;
import com.project.Quereria.dto.response.RegisterResponse;
import com.project.Quereria.entity.User;
import com.project.Quereria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public RegisterResponse register(RegisterRequest request) {
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

        return new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "Пользователь успешно зарегистрирован"
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}