package com.bussinessmanagement.managementSystem.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.*;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.TicketRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.TicketResponseDTO;
import com.bussinessmanagement.managementSystem.Repositories.*;
import com.bussinessmanagement.managementSystem.enums.*;

@Service
@RequiredArgsConstructor
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private TicketPdfService ticketPdfService;

    public byte[] comprarBilheteComPdf(TicketRequestDTO dto) throws Exception {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (user.getRole() != Role.CLIENT) {
            throw new RuntimeException("Apenas clientes podem comprar bilhetes.");
        }

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (event.getBilhetesVendidos() >= event.getLotacaoTotal()) {
            throw new RuntimeException("Evento esgotado.");
        }

        // Criar bilhete
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setClient(user);
        ticket.setTipo(dto.tipo());
        ticket.setFormato(dto.formato());
        ticket.setEstado(TicketState.VALIDO);
        ticket.setQrCode(gerarQrUnico());

        event.setBilhetesVendidos(event.getBilhetesVendidos() + 1);

        Ticket saved = ticketRepository.save(ticket);

        // Gerar QR code
        byte[] qr = qrCodeService.gerarImagemQRCode(saved.getQrCode());

        // Gerar PDF do bilhete
        byte[] pdf = ticketPdfService.gerarPdf(saved, qr);

        return pdf; // Retorna o PDF para o front-end
    }

    private String gerarQrUnico() {
        String qr;
        do {
            qr = UUID.randomUUID().toString();
        } while (ticketRepository.existsByQrCode(qr));
        return qr;
    }

    public Page<TicketResponseDTO> listarTicketsVendidos(Pageable pageable) {

        List<TicketState> estados = List.of(
                TicketState.VALIDO,
                TicketState.USADO);

        return ticketRepository
                .findByEstadoIn(estados, pageable)
                .map(this::toDTO);
    }

    public Page<TicketResponseDTO> listarTicketsPorEvento(
            Long eventId,
            Pageable pageable) {

        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Evento não encontrado");
        }

        return ticketRepository
                .findByEventId(eventId, pageable)
                .map(this::toDTO);
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

    private TicketResponseDTO toDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getEvent().getName(),
                ticket.getClient().getId(),
                ticket.getClient().getFullName(),
                ticket.getTipo(),
                ticket.getFormato(),
                ticket.getEstado(),
                ticket.getQrCode());
    }
}