package com.bussinessmanagement.managementSystem.Models.DTOs.Request;

import com.bussinessmanagement.managementSystem.enums.TicketType;
import com.bussinessmanagement.managementSystem.enums.TicketFormat;

public record TicketRequestDTO(
        Long eventId,
        TicketType tipo,
        TicketFormat formato
) {}