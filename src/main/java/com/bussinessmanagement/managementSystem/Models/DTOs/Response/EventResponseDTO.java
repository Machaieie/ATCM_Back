package com.bussinessmanagement.managementSystem.Models.DTOs.Response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.bussinessmanagement.managementSystem.enums.EventStatus;

public record EventResponseDTO(
     Long id,
        String name,
        String description,
        LocalDate date,
        LocalTime time,
        String location,
        Double precoNormal,
        Double precoVip,
        Integer lotacaoTotal,
        Integer bilhetesVendidos,
        EventStatus estado
) {
    
}
