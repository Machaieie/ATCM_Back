package com.bussinessmanagement.managementSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bussinessmanagement.managementSystem.Models.DTOs.Request.LoginRequest;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.RegisterRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.UpdateUser;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.AuthenticationResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.LoginResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.UpdateUserResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.UsersResponse;
import com.bussinessmanagement.managementSystem.Services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AutenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    // 🔐 REGISTO
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequestDTO request) {

        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    // 🔐 ALTERAR SENHA
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String username,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha) {

        String response = authenticationService.alterarSenha(
                username, senhaAtual, novaSenha);
        return ResponseEntity.ok(response);
    }

    // 👤 ATUALIZAR USUÁRIO
    @PutMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUser dto) {

        UpdateUserResponse response = authenticationService.updateUser(id, dto);
        return ResponseEntity.ok(response);
    }

     @GetMapping("/users")
    public ResponseEntity<List<UsersResponse>> getAll()
             {

        return ResponseEntity.ok(authenticationService.getAllUsers());
    }


}
