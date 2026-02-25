package com.shop.storix.portalapi.config.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.storix.portalapi.config.auth.exception.JwtAuthenticationException;
import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");

        TokenStatus status = resolveStatus(authException);

        String message = switch (status) {
            case EXPIRED -> "JWT_EXPIRED";
            case INVALID -> "JWT_INVALID";
            default -> "UNAUTHORIZED";
        };

        exceptionHandler(response, message);
    }

    private void exceptionHandler(HttpServletResponse response, String message) {
        try {
            ApiResponse<Void> body = ApiResponse.fail("401", message, null);
            objectMapper.writeValue(response.getWriter(), body);

        } catch (Exception e) {
            log.error("EntryPoint Error: ", e);
        }
    }

    private TokenStatus resolveStatus(AuthenticationException ex) {
        Throwable cur = ex;
        while (cur != null) {
            if (cur instanceof JwtAuthenticationException jwtEx) {
                return jwtEx.getTokenStatus();
            }
            cur = cur.getCause();
        }
        return TokenStatus.MISSING;
    }
}
