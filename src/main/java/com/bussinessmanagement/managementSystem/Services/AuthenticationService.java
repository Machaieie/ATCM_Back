package com.bussinessmanagement.managementSystem.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bussinessmanagement.managementSystem.Exceptions.DuplicatedEntitiesException;
import com.bussinessmanagement.managementSystem.Models.User;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.LoginRequest;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.RegisterRequestDTO;
import com.bussinessmanagement.managementSystem.Models.DTOs.Request.UpdateUser;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.AuthenticationResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.LoginResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.UpdateUserResponse;
import com.bussinessmanagement.managementSystem.Models.DTOs.Response.UsersResponse;
import com.bussinessmanagement.managementSystem.Repositories.UserRepository;
import com.bussinessmanagement.managementSystem.enums.UserState;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicatedEntitiesException("Este email ja foi registado no sistema, tente outro");
        }

        if (userRepository.existsByPhone(request.phone())) {
            throw new DuplicatedEntitiesException("Este numero ja foi registado no sistema, tente registar outro");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicatedEntitiesException("Este username ja foi registado no sistema, tente outro");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setState(UserState.ACTIVE);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                "Usuario criado com sucesso!");
    }

    public LoginResponse authenticate(LoginRequest request) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password());
            var user = userRepository.findByUsername(request.username()).orElseThrow();
            var auth = this.authenticationManager.authenticate(usernamePassword);
            User usuario = (User) auth.getPrincipal();
            String accessToken = jwtService.generateToken(usuario);
            var refreshToken = jwtService.generateRefreshToken(user);

            return new LoginResponse(
                    accessToken,
                    refreshToken);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }
    }

    public String alterarSenha(String username, String senhaAtual, String novaSenha) {

        if (senhaAtual.equals(novaSenha)) {
            throw new BadCredentialsException("A nova senha deve ser diferente da senha atual");
        }

        if (novaSenha.length() < 8) {
            throw new BadCredentialsException("A senha deve ter no mínimo 8 caracteres");
        }

        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new BadCredentialsException("Senha atual incorreta");
        }
        usuario.setPassword(passwordEncoder.encode(novaSenha));
        userRepository.save(usuario);

        String assunto = "Alteração de Senha Confirmada";
        String corpoEmail = "Olá " + usuario.getFullName() + ",\n\n"
                + "Sua senha foi alterada com sucesso em " + LocalDateTime.now() + ".\n\n"
                + "Caso não tenha sido você quem realizou esta alteração, entre em contato imediatamente com o suporte.\n\n"
                + "Atenciosamente,\nEquipe de Suporte";
        // emailService.send(usuario.getEmail(), assunto, corpoEmail);
        return "Senha alterada com sucesso";
    }

    public UpdateUserResponse updateUser(Long id, UpdateUser dto) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com ID " + id + " não encontrado."));
        userRepository.findByEmail(dto.email()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new DuplicatedEntitiesException("Já existe um usuário com este e-mail.");
            }
        });
        userRepository.findByUsername(dto.username()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new DuplicatedEntitiesException("Já existe um usuário com este username.");
            }
        });
        usuario.setFullName(dto.fullName());
        usuario.setEmail(dto.email());
        usuario.setUsername(dto.username());
        usuario.setPhone(dto.phone());
        usuario = userRepository.save(usuario);

        return new UpdateUserResponse(
                "Usuario atualizado com sucesso");
    }

    public List<UsersResponse> getAllUsers (){
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UsersResponse(
            user.getId(),
            user.getFullName(),
            user.getPhone(),
            user.getUsername(),
            user.getEmail(),
            user.getState()

        )).toList();
    }

}
