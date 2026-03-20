package com.project.Quereria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // отключаем CSRF для теста
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/quizzes/**").permitAll() // разрешаем POST на квизы
                        .anyRequest().authenticated()                   // остальное требует аутентификации
                );

        return http.build();
    }
}

