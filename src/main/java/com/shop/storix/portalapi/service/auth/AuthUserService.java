package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.AuthUser;

public interface AuthUserService {
    AuthUser getUserAuth(String userLoginNo);


}
