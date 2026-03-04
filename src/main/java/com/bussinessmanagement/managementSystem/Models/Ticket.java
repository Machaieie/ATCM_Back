package com.bussinessmanagement.managementSystem.Models;


import com.bussinessmanagement.managementSystem.enums.TicketState;
import com.bussinessmanagement.managementSystem.enums.TicketType;
import com.bussinessmanagement.managementSystem.enums.TicketFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Relacionamento com Evento
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // 🔗 Relacionamento com Cliente
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Enumerated(EnumType.STRING)
    private TicketType tipo;

    @Column(unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private TicketState estado;

    @Enumerated(EnumType.STRING)
    private TicketFormat formato;
}