package com.bussinessmanagement.managementSystem.Models.DTOs.Request;

import com.bussinessmanagement.managementSystem.enums.PaymentMethod;

public record PaymentRequestDTO(
        Long eventId,
        PaymentMethod metodoPagamento,
        Double valor
) {}