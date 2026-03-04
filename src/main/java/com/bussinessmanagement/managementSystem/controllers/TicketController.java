package com.bussinessmanagement.managementSystem.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Ticket;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.TicketRequestDTO;
import com.bussinessmanagement.managementSystem.Services.TicketService;
import com.bussinessmanagement.managementSystem.Services.QRCodeService;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QRCodeService qrCodeService;

    // 🎫 Comprar bilhete
    @PostMapping("/comprar")
    public ResponseEntity<Ticket> comprar(@RequestBody TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.comprarBilhete(dto));
    }

    // 🔎 Validar QR (scan)
    @PostMapping("/validar/{qrCode}")
    public ResponseEntity<String> validar(@PathVariable String qrCode) {
        return ResponseEntity.ok(ticketService.validarBilhete(qrCode));
    }

    // 📥 Download QR Code em imagem
    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<byte[]> gerarQR(@PathVariable String qrCode) throws Exception {

        byte[] imagem = qrCodeService.gerarImagemQRCode(qrCode);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qr.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(imagem);
    }
}