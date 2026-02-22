package com.shop.storix.portalapi.config.auth.filter;

import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.model.dto.auth.domain.*;
import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.userInfo.OAuth2UserInfo;
import com.shop.storix.portalapi.model.dto.auth.ProviderInfo;
import com.shop.storix.portalapi.model.dto.auth.userInfo.OAuth2UserInfoFactory;
import com.shop.storix.portalapi.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2LoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final LoginMapper loginMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String key = userRequest.getClientRegistration()
                                .getProviderDetails()
                                .getUserInfoEndpoint()
                                .getUserNameAttributeName();

        // 서비스를 구분하는 코드
        String providerCode = userRequest.getClientRegistration().getRegistrationId();

        // 어떤 소셜로그인을 사용했는지 반환받는 정적 메서드
        ProviderInfo providerInfo = ProviderInfo.from(providerCode);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        //
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerInfo, attributes);
        String userIdentifier = oAuth2UserInfo.getOAuth2Id();

        Login user = getLogin(userIdentifier, providerInfo);

        OAuthLogin oAuthLogin = loginMapper.findOAuthLoginByOAuthInfo(userIdentifier,providerInfo.name()).orElseThrow();

        List<Role> roles = loginMapper.findUserRoleByLoginNo(user);

        return new UserPrincipal(user,oAuthLogin,roles, attributes, key);
    }

    private Login getLogin(String userIdentifier, ProviderInfo providerInfo) {
        Optional<Login> optionalLogin = loginMapper.findLoginByOAuthInfo(userIdentifier, providerInfo.name());

        if (optionalLogin.isEmpty()) {
            String loginUuid = UuidUtil.randomUuid();
            Login unregisteredUser = new Login(loginUuid,userIdentifier,userIdentifier,"active");

            loginMapper.insertUserLogin(unregisteredUser);

            /*OAuthLogin 테이블*/
            String OAuthloginUuid = UuidUtil.randomUuid();
            OAuthLogin oAuthLoginInfo = new OAuthLogin(OAuthloginUuid,unregisteredUser.userLoginNo(), providerInfo.name(),userIdentifier);
            loginMapper.insertUserOauth(oAuthLoginInfo);

            /*Role Login 테이블*/

            UserRole userRole = new UserRole(loginUuid, "ROLE_001");// HACK: 임시로 역할 넣는 거 구현
            loginMapper.insertUserRole(userRole);

            /*사용자 정보 없을떄 구매자 정보도 같이 저장*/

            return unregisteredUser;
        }
        return optionalLogin.orElseThrow(); // HACK: 에러 처리 해야함
    }


}
