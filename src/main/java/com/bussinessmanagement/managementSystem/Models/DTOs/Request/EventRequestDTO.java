package com.bussinessmanagement.managementSystem.Models.DTOs.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventRequestDTO(
        String name,
        String description,
        LocalDate date,
        LocalTime time,
        String location,
        Double precoNormal,
        Double precoVip,
        Integer lotacaoTotal
) {}