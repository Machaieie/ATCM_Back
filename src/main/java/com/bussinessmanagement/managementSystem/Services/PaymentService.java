package com.bussinessmanagement.managementSystem.Services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.*;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.PaymentRequestDTO;
import com.bussinessmanagement.managementSystem.Repositories.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EventRepository eventRepository;

    public Payment pagar(PaymentRequestDTO dto) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Payment payment = new Payment();
        payment.setClient(user);
        payment.setEvent(event);
        payment.setMetodoPagamento(dto.metodoPagamento());
        payment.setValor(dto.valor());
        payment.setDataPagamento(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}