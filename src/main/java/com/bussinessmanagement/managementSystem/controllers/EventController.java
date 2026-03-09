package com.bussinessmanagement.managementSystem.controllers;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Event;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.EventRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.EventResponseDTO;
import com.bussinessmanagement.managementSystem.Services.EventService;
import com.bussinessmanagement.managementSystem.enums.EventStatus;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/criar")
    public ResponseEntity<EventResponseDTO> criar(@RequestBody EventRequestDTO dto) {
        return ResponseEntity.ok(eventService.criarEvento(dto));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) EventStatus estado,
            Pageable pageable) {
        return ResponseEntity.ok(
                eventService.listarComFiltro(nome, data, estado, pageable));
    }
}