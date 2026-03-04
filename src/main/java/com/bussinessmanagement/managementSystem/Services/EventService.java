package com.bussinessmanagement.managementSystem.Services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.managementSystem.Models.Event;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.EventRequestDTO;
import com.bussinessmanagement.managementSystem.Repositories.EventRepository;
import com.bussinessmanagement.managementSystem.enums.EventStatus;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event criarEvento(EventRequestDTO dto) {

        Event event = new Event();
        event.setName(dto.name());
        event.setDescription(dto.description());
        event.setDate(dto.date());
        event.setTime(dto.time());
        event.setLocation(dto.location());
        event.setPrecoNormal(dto.precoNormal());
        event.setPrecoVip(dto.precoVip());
        event.setLotacaoTotal(dto.lotacaoTotal());
        event.setEstado(EventStatus.ATIVO);

        return eventRepository.save(event);
    }
}