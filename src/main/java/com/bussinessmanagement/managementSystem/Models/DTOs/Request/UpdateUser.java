package com.bussinessmanagement.managementSystem.Models.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUser(
    String fullName,
        String username,
        @NotBlank(message = "O número de telefone não pode estar vazio")
        @Pattern(
                regexp = "\\+258(8[726345])\\d{6}$",
                message = "O número de telefone deve estar no formato +2588X... com prefixos válidos: 87, 86, 82, 83, 84 ou 85"
        )
        String phone,
        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "Email inválido")
        String email
) {
    
}
