package com.bussinessmanagement.managementSystem.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussinessmanagement.managementSystem.Models.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByPhone(
            String phone);

    boolean existsByEmail(
            String email);

    Optional<User> findByEmail(
            String email);

    boolean existsByUsername(String username); 
    
}
