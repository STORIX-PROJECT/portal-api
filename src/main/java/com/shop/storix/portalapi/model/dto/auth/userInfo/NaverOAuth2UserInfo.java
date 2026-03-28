package com.shop.storix.portalapi.model.dto.auth.userInfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getOAuth2Id() {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        return response.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        return response.get("email").toString();
    }
}
