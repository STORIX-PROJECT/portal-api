package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.config.auth.exception.JwtAuthenticationException;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.repository.auth.RefreshTokenRepository;
import com.shop.storix.portalapi.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public void save(AuthDto.Token token) {
        refreshTokenRepository.save(token.userLoginNo(),token.refreshToken());
    }

    public AuthDto.Token findById(String userLoginNo) {
        return refreshTokenRepository.findByUserLoginNo(userLoginNo)
                .orElseThrow(()-> new JwtAuthenticationException(TokenStatus.INVALID));
    }

    public void delete(String userLoginNo) {
        refreshTokenRepository.deleteByUserLoginNo(userLoginNo);
    }

}
