package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;

public interface RegisterService {
    void signUp(AuthDto.SignUpRequest dto);
}
