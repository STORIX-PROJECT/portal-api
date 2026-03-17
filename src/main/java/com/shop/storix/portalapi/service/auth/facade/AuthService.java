package com.shop.storix.portalapi.service.auth.facade;

import com.shop.storix.portalapi.config.auth.exception.JwtAuthenticationException;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.service.auth.AuthUserService;
import com.shop.storix.portalapi.service.auth.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private static final String REFRESH_COOKIE_NAME = "REFRESH_TOKEN";

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final AuthUserService authUserService;    // Login + Roles 조회

    public void reissueToken(HttpServletRequest request , HttpServletResponse response) {
        /*cookie 이름 나중에 상수로 관리*/
        String refreshToken = jwtProvider.resolveRefreshToken(request, REFRESH_COOKIE_NAME);

        TokenStatus status = jwtProvider.validateRefreshToken(refreshToken);
        if (status != TokenStatus.AUTHENTICATED) {
            throw new JwtAuthenticationException(status);
        }

        Claims claims = jwtProvider.parseRefreshClaims(refreshToken);

        String userLoginNo = claims.getSubject();
        if (!StringUtils.hasText(userLoginNo)) {
            throw new JwtAuthenticationException(TokenStatus.INVALID);
        }

        AuthDto.Token saved = tokenService.findById(userLoginNo);

        if (!refreshToken.equals(saved.refreshToken())) {
            throw new JwtAuthenticationException(TokenStatus.INVALID);
        }

        AuthDto.AuthUser authUser = authUserService.getUserAuth(userLoginNo);

        UserPrincipal userPrincipal = toPrincipal(authUser);

        String newAccessToken = jwtProvider.generateAccessToken(userPrincipal);
        String newRefreshToken = jwtProvider.generateRefreshToken(userPrincipal);

        Cookie accessCookie = jwtProvider.createCookie(
                "ACCESS_TOKEN",
                newAccessToken,
                1200000 // 임시
        );

        ResponseCookie refreshCookie = jwtProvider.setTokenToCookie("REFRESH_TOKEN", newRefreshToken, 1200000 /*임시*/ / 1000);

        response.addCookie(accessCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private static UserPrincipal toPrincipal(AuthDto.AuthUser authUser) {
        return new UserPrincipal(authUser.login(), authUser.roles());
    }
}
