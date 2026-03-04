package com.bussinessmanagement.managementSystem.Models.DTOs.Response;

import com.bussinessmanagement.managementSystem.enums.TicketFormat;
import com.bussinessmanagement.managementSystem.enums.TicketState;
import com.bussinessmanagement.managementSystem.enums.TicketType;

public record TicketResponseDTO(
        Long id,

        Long eventId,
        String eventName,

        Long clientId,
        String clientName,

        TicketType tipo,
        TicketFormat formato,

        TicketState estado,

        String qrCode) {

}
