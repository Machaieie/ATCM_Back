package com.bussinessmanagement.managementSystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Ticket;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.TicketRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.TicketResponseDTO;
import com.bussinessmanagement.managementSystem.Repositories.TicketRepository;
import com.bussinessmanagement.managementSystem.Services.TicketService;
import com.bussinessmanagement.managementSystem.Services.QRCodeService;
import com.bussinessmanagement.managementSystem.Services.TicketPdfService;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final QRCodeService qrCodeService;
    private final TicketPdfService ticketPdfService;
      private final TicketRepository ticketRepository;

  @PostMapping("/buy")
public ResponseEntity<?> comprarBilhete(@RequestBody TicketRequestDTO dto) {
    try {
        byte[] pdf = ticketService.comprarBilheteComPdf(dto);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=bilhete.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    } catch (Exception e) {
        // Logar stack trace completo
        e.printStackTrace();

        // Retornar mensagem de erro no corpo para debug
        return ResponseEntity.status(500)
                .body("Erro ao comprar bilhete: " + e.getMessage());
    }
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

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable Long id) throws Exception {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado"));

        byte[] qr = qrCodeService.gerarImagemQRCode(ticket.getQrCode());

        byte[] pdf = ticketPdfService.gerarPdf(ticket, qr);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=bilhete.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}