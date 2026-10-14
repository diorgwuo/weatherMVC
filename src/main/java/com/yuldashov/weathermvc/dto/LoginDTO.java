package com.yuldashov.weathermvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Login не может быть пустым")
        @Size(min = 4, max = 20, message = "Login должен содержать от 4 до 20 символов")
        String login,
        @NotBlank(message = "Password не может быть пустым")
        @Size(min = 4, message = "Password должен содержать от 4 символов")
        String password
){}
