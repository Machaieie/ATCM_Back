package com.bussinessmanagement.managementSystem.Services;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.bussinessmanagement.Specifications.EventSpecification;
import com.bussinessmanagement.managementSystem.Models.Event;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.EventRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.EventResponseDTO;
import com.bussinessmanagement.managementSystem.Repositories.EventRepository;
import com.bussinessmanagement.managementSystem.enums.EventStatus;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponseDTO criarEvento(EventRequestDTO dto) {

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
        event.setBilhetesVendidos(0);
       return toDTO(eventRepository.save(event));
    }

    public Page<EventResponseDTO> listarComFiltro(
        String nome,
        LocalDate data,
        EventStatus estado,
        Pageable pageable
) {

    Specification<Event> spec =
            EventSpecification.filtroDinamico(nome, data, estado);

    Page<Event> page = eventRepository.findAll(spec, pageable);

    return new PageImpl<>(
            page.getContent()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList()),
            pageable,
            page.getTotalElements()
    );
}

    private EventResponseDTO toDTO(Event event) {
    return new EventResponseDTO(
            event.getId(),
            event.getName(),
            event.getDescription(),
            event.getDate(),
            event.getTime(),
            event.getLocation(),
            event.getPrecoNormal(),
            event.getPrecoVip(),
            event.getLotacaoTotal(),
            event.getBilhetesVendidos(),
            event.getEstado()
    );
}
}