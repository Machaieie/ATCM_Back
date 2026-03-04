package com.bussinessmanagement.managementSystem.Models.DTOs.Response;

public record AuthenticationResponse(
     String accessToken,
        String refreshToken,
        String message
) {
    
}
