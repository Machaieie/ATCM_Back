package com.bussinessmanagement.managementSystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.DTOs.Request.TicketRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.TicketResponseDTO;
import com.bussinessmanagement.managementSystem.Services.TicketService;
import com.bussinessmanagement.managementSystem.Services.QRCodeService;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QRCodeService qrCodeService;

    @PostMapping("/comprar")
    public ResponseEntity<TicketResponseDTO> comprar(@RequestBody TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.comprarBilhete(dto));
    }

    @GetMapping("/vendidos")
    public ResponseEntity<Page<TicketResponseDTO>> listarVendidos(
            Pageable pageable) {
        return ResponseEntity.ok(
                ticketService.listarTicketsVendidos(pageable));
    }

    @GetMapping("/evento/{eventId}")
    public ResponseEntity<Page<TicketResponseDTO>> listarPorEvento(
            @PathVariable Long eventId,
            Pageable pageable) {
        return ResponseEntity.ok(
                ticketService.listarTicketsPorEvento(eventId, pageable));
    }

    @PostMapping("/validar/{qrCode}")
    public ResponseEntity<String> validar(@PathVariable String qrCode) {
        return ResponseEntity.ok(ticketService.validarBilhete(qrCode));
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<byte[]> gerarQR(@PathVariable String qrCode) throws Exception {

        byte[] imagem = qrCodeService.gerarImagemQRCode(qrCode);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qr.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(imagem);
    }
}