package com.shop.storix.portalapi.service.auth;
import com.shop.storix.portalapi.model.dto.auth.request.SignUpRequest;

public interface RegisterService {
    void signUp(SignUpRequest dto);
}
