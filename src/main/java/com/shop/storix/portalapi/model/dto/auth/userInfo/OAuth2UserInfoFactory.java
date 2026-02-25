package com.shop.storix.portalapi.model.dto.auth.userInfo;

import com.shop.storix.portalapi.model.dto.auth.ProviderInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderInfo providerInfo, Map<String, Object> attributes) {
       return  switch (providerInfo) {
           case NAVER -> new NaverOAuth2UserInfo(attributes);
           case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
       };
    }
}