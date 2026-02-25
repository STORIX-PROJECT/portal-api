package com.shop.storix.portalapi.model.dto.auth.userInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;
@RequiredArgsConstructor
public abstract class OAuth2UserInfo {
    protected final Map<String,Object> attributes;

    public abstract String getOAuth2Id();
}
