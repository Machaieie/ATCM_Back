package com.bussinessmanagement.managementSystem.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String json = String.format(
            """
            {
              "timestamp": "%s",
              "status": 401,
              "error": "Unauthorized",
              "message": "Não autorizado: é necessário autenticar-se para acessar este recurso",
              "path": "%s"
            }
            """,
            Instant.now().toString(),
            request.getRequestURI()
        );

        response.getWriter().write(json);
    }
}