package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.Token;

public interface TokenService {
    void save(Token token);
    Token findById (String userLoginNo);
}
