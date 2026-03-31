package com.shop.storix.portalapi.service.auth;

public interface LoginService {
    String findLoginIdByEmail(String email);
    boolean existsOauthByEmail(String email);
}
