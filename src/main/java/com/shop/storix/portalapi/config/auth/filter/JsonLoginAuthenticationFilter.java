package com.shop.storix.portalapi.config.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;

public class JsonLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonLoginAuthenticationFilter() {
        super( PathPatternRequestMatcher.withDefaults()
                .matcher(HttpMethod.POST,"/api/v1/login"));
    }

    @Override
    public @Nullable Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        AuthDto.SignInRequest signInRequest = objectMapper.readValue(request.getInputStream(), AuthDto.SignInRequest.class);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(
                        signInRequest.id(),
                        signInRequest.password()
                );
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
