package com.bussinessmanagement.managementSystem.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussinessmanagement.managementSystem.Models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>     {

 Optional<Token> findByAccessToken(String token);
    
}
