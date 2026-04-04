package com.project.Quereria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String name;
    private String email;
}