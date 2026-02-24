package com.shop.storix.portalapi.config.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class FailureHandler implements AuthenticationFailureHandler {

    private final String redirectUrl;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public FailureHandler(@Value("${storix.web-main-url}") String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectUri = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("error", URLEncoder.encode(exception.getLocalizedMessage()), StandardCharsets.UTF_8)
                .build()
                .toUriString();

        redirectStrategy.sendRedirect(request, response, redirectUri);
    }
}
