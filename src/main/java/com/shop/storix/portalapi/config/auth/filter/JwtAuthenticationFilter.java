package com.shop.storix.portalapi.config.auth.filter;

import com.shop.storix.portalapi.config.auth.exception.JwtAuthenticationException;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.service.auth.facade.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.resolveAccessToken(request);

        // 1. 토큰이 없으면 그냥 통과
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2.상태 판단
        TokenStatus status = jwtProvider.validateToken(accessToken);

        switch (status) {
            case AUTHENTICATED -> {
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request,response);
            }
            case EXPIRED -> {
                SecurityContextHolder.clearContext();
                throw new JwtAuthenticationException(TokenStatus.EXPIRED);
            }
            case INVALID -> {
                SecurityContextHolder.clearContext();
                throw new JwtAuthenticationException(TokenStatus.INVALID);
            }
        }


    }
}
