package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.config.auth.exception.JwtAuthenticationException;
import com.shop.storix.portalapi.mapper.auth.TokenMapper;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.model.dto.auth.domain.Token;
import com.shop.storix.portalapi.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenMapper tokenMapper;

    @Transactional
    public void save(Token token) {
        tokenMapper.save(token);
    }

    public Token findById(String identifier) {
        return tokenMapper.findById(identifier)
                .orElseThrow(()-> new JwtAuthenticationException(TokenStatus.INVALID));
    }

//
//    public void deleteById(String identifier) {
//        tokenMapper.deleteById(identifier);
//    }
}
