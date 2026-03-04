package com.bussinessmanagement.managementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussinessmanagement.managementSystem.Models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
    boolean existsById(Long id);
}
