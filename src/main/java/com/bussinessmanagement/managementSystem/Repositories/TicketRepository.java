package com.bussinessmanagement.managementSystem.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussinessmanagement.managementSystem.Models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    Optional<Ticket> findByQrCode(String qrCode);

    boolean existsByQrCode(String qrCode);
}