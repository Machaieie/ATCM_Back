package com.bussinessmanagement.managementSystem.Services;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.*;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.TicketRequestDTO;
import com.bussinessmanagement.managementSystem.Repositories.*;
import com.bussinessmanagement.managementSystem.enums.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public Ticket comprarBilhete(TicketRequestDTO dto) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // ✅ Apenas CLIENT pode comprar
        if (user.getRole() != Role.CLIENT) {
            throw new RuntimeException("Apenas clientes podem comprar bilhetes.");
        }

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        // ✅ Verificar lotação
        if (event.getBilhetesVendidos() >= event.getLotacaoTotal()) {
            throw new RuntimeException("Evento esgotado.");
        }

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setClient(user);
        ticket.setTipo(dto.tipo());
        ticket.setFormato(dto.formato());
        ticket.setEstado(TicketState.VALIDO);

        // 🔥 Gerar QR único
        ticket.setQrCode(gerarQrUnico());

        // atualizar contagem
        event.setBilhetesVendidos(event.getBilhetesVendidos() + 1);

        return ticketRepository.save(ticket);
    }

    private String gerarQrUnico() {
        String qr;
        do {
            qr = UUID.randomUUID().toString();
        } while (ticketRepository.existsByQrCode(qr));

        return qr;
    }

    // 🎯 VALIDAÇÃO DO QR
    public String validarBilhete(String qrCode) {

        Ticket ticket = ticketRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado"));

        if (ticket.getEstado() == TicketState.USADO) {
            return "Bilhete já utilizado ❌";
        }

        if (ticket.getEstado() == TicketState.CANCELADO) {
            return "Bilhete cancelado ❌";
        }

        // 🔥 INVALIDAR APÓS SCAN
        ticket.setEstado(TicketState.USADO);
        ticketRepository.save(ticket);

        return "Bilhete válido ✅ Entrada permitida";
    }
}