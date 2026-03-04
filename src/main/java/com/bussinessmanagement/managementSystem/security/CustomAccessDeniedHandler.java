package com.bussinessmanagement.managementSystem.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        String json = String.format(
            """
            {
              "timestamp": "%s",
              "status": 403,
              "error": "Forbidden",
              "message": "Acesso negado: você não tem permissão para acessar este recurso",
              "path": "%s"
            }
            """,
            Instant.now().toString(),
            request.getRequestURI()
        );

        response.getWriter().write(json);
    }
}