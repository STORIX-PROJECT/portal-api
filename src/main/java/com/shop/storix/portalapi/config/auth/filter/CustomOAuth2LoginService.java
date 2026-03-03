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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
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

        // 소셜 종류에 따른 사용자 OAuth 정보 반환
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerInfo, attributes);
        String userIdentifier = oAuth2UserInfo.getOAuth2Id();

        AuthDto.Login user = getLogin(userIdentifier, providerInfo);

        AuthDto.OAuthLogin oAuthLoginDto = loginMapper.findOAuthLoginByOAuthInfo(userIdentifier,providerInfo.name()).orElseThrow();

        List<AuthDto.Role> roles = loginMapper.findUserRoleByLoginNo(user);

        return new UserPrincipal(user, oAuthLoginDto,roles, attributes, key);
    }

    private AuthDto.Login getLogin(String userIdentifier, ProviderInfo providerInfo) {
        Optional<AuthDto.Login> optionalLogin = loginMapper.findLoginByOAuthInfo(userIdentifier, providerInfo.name());

        if (optionalLogin.isEmpty()) {
            String loginUuid = UuidUtil.randomUuid();
            AuthDto.Login unregisteredUser = new AuthDto.Login(loginUuid, userIdentifier, userIdentifier, "active");

            loginMapper.insertUserLogin(unregisteredUser);

            /*OAuthLogin 테이블*/
            String OAuthloginUuid = UuidUtil.randomUuid();
            AuthDto.OAuthLogin oAuthLoginDtoInfo = new AuthDto.OAuthLogin(OAuthloginUuid,unregisteredUser.userLoginNo(), providerInfo.name(),userIdentifier);
            loginMapper.insertUserOauth(oAuthLoginDtoInfo);

            /*Role Login 테이블*/

            AuthDto.UserRole userRole = new AuthDto.UserRole(loginUuid, "ROLE_001");// HACK: 임시로 역할 넣는 거 구현
            loginMapper.insertUserRole(userRole);

            /*사용자 정보 없을떄 구매자 정보도 같이 저장*/

            return unregisteredUser;
        }
        return optionalLogin.orElseThrow(); // HACK: 에러 처리 해야함
    }
}
