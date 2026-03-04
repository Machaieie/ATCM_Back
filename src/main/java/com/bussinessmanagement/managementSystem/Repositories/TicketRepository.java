package com.bussinessmanagement.managementSystem.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussinessmanagement.managementSystem.Models.Ticket;
import com.bussinessmanagement.managementSystem.enums.TicketState;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByQrCode(String qrCode);

    boolean existsByQrCode(String qrCode);

    Page<Ticket> findByEstadoIn(List<TicketState> estados, Pageable pageable);

    Page<Ticket> findByEventId(Long eventId, Pageable pageable);
}