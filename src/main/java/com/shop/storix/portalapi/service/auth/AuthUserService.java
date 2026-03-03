package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;

public interface AuthUserService {
    AuthDto.AuthUser getUserAuth(String userLoginNo);


}
