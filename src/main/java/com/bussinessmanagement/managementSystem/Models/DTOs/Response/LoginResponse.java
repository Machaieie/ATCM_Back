package com.bussinessmanagement.managementSystem.Models.DTOs.Response;

public record LoginResponse(
     String accessToken,
        String refreshToken
) {
    
}
