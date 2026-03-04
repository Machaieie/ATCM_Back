package com.bussinessmanagement.managementSystem.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import com.bussinessmanagement.managementSystem.enums.EventStatus;

import jakarta.persistence.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Table( name = "Event")
@Entity
@Getter
@Setter
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private LocalDate date;

    private LocalTime time;

    private String location;

    private Double precoNormal;

    private Double precoVip;

    private Integer lotacaoTotal;

    private Integer bilhetesVendidos;

    @Enumerated(EnumType.STRING)
    private EventStatus estado;
}
