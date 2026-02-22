package com.shop.storix.portalapi.config.auth.exception;

import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    private final TokenStatus tokenStatus;

    public JwtAuthenticationException(TokenStatus tokenStatus) {
        super(tokenStatus.name());
        this.tokenStatus = tokenStatus;
    }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }
}
