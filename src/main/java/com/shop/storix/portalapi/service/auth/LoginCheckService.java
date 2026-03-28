package com.shop.storix.portalapi.service.auth;

public interface LoginCheckService {
    void handleLoginSuccess(String loginId);
    void handleLoginFailure(String loginId , int failCount);
    boolean checkUserProfile(String loginNo);
}
