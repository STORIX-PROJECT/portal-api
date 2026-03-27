package com.shop.storix.portalapi.config.auth.handler;

import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.service.auth.facade.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    public SuccessHandler(@Value("${storix.web-main-url}") String redirectUrl,
                          @Value("${jwt.access-expiration}") int accessExpiration,
                          @Value("${jwt.refresh-expiration}") int refreshExpiration,
                          JwtProvider jwtProvider
    ) {
        this.redirectUrl = redirectUrl;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.jwtProvider = jwtProvider;
    }

    private final String redirectUrl;
    private final int accessExpiration;
    private final int refreshExpiration;
    private final JwtProvider jwtProvider;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) principal;
        String accessToken = jwtProvider.generateAccessToken(userPrincipal);

        Cookie accessCookie = jwtProvider.createCookie(
                "ACCESS_TOKEN",
                accessToken,
                accessExpiration
        );
        String refreshToken = jwtProvider.generateRefreshToken(userPrincipal);

        ResponseCookie refreshCookie = jwtProvider.setTokenToCookie("REFRESH_TOKEN", refreshToken,
                refreshExpiration);

        response.addCookie(accessCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

}
