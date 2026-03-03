package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;

public interface TokenService {
    void save(AuthDto.Token token);
    AuthDto.Token findById (String userLoginNo);
}
