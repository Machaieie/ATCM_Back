package com.bussinessmanagement.managementSystem.Models;

import java.time.LocalDateTime;

import com.bussinessmanagement.managementSystem.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodoPagamento;

    

    private LocalDateTime dataPagamento;
}
