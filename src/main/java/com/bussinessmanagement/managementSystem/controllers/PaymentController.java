package com.bussinessmanagement.managementSystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Payment;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.PaymentRequestDTO;
import com.bussinessmanagement.managementSystem.Services.PaymentService;

@RestController
@RequestMapping("api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pagar")
    public ResponseEntity<Payment> pagar(@RequestBody PaymentRequestDTO dto) {
        return ResponseEntity.ok(paymentService.pagar(dto));
    }
}