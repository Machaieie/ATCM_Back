package com.bussinessmanagement.managementSystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Event;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.EventRequestDTO;
import com.bussinessmanagement.managementSystem.Services.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/criar")
    public ResponseEntity<Event> criar(@RequestBody EventRequestDTO dto) {
        return ResponseEntity.ok(eventService.criarEvento(dto));
    }
}