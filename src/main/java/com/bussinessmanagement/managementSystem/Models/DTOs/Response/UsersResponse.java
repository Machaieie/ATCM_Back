package com.bussinessmanagement.managementSystem.Models.DTOs.Response;

import com.bussinessmanagement.managementSystem.enums.UserState;

public record UsersResponse(
     Long id,
        String fullname,
        String phone,
        String username,
        String email,
        UserState estado
) {
    
}
