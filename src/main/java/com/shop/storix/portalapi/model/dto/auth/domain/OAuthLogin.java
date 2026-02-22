package com.shop.storix.portalapi.model.dto.auth.domain;

public record OAuthLogin
(
    String userOauthNo,
    String userLoginNo,
    String provider,
    String providerId
){}
