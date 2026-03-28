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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private static final String REFRESH_COOKIE_NAME = "REFRESH_TOKEN";
    private static final String ACCESS_COOKIE_NAME ="ACCESS_TOKEN";

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final AuthUserService authUserService;    // Login + Roles 조회

    private final int ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    public AuthService(JwtProvider jwtProvider,
                       TokenService tokenService,
                       AuthUserService authUserService,
                       @Value("${jwt.access-expiration}") int ACCESS_EXPIRATION,
                       @Value("${jwt.refresh-expiration}") long REFRESH_EXPIRATION
    ) {
        this.jwtProvider = jwtProvider;
        this.tokenService = tokenService;
        this.authUserService = authUserService;
        this.ACCESS_EXPIRATION = ACCESS_EXPIRATION;
        this.REFRESH_EXPIRATION = REFRESH_EXPIRATION;
    }

    public void reissueToken(HttpServletRequest request , HttpServletResponse response) {
        /*cookie 이름 나중에 상수로 관리*/
        String refreshToken = jwtProvider.resolveRefreshToken(request, REFRESH_COOKIE_NAME);

        AuthDto.TokenResult result = jwtProvider.validateRefreshToken(refreshToken);
        if (result.status() != TokenStatus.AUTHENTICATED) {
            throw new JwtAuthenticationException(result.status());
        }

        Claims claims = result.claims();

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
                ACCESS_EXPIRATION
        );

        ResponseCookie refreshCookie = jwtProvider.setTokenToCookie(
                "REFRESH_TOKEN",
                newRefreshToken,
                REFRESH_EXPIRATION
        );

        response.addCookie(accessCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    public void deleteRefreshToken (HttpServletRequest request ,HttpServletResponse response){
        String refreshToken = jwtProvider.resolveRefreshToken(request, REFRESH_COOKIE_NAME);

        // 1. 쿠키 만료 체크
        if (refreshToken == null) {
            throw new JwtAuthenticationException(TokenStatus.EXPIRED);
        }

        // 2. 토큰 검증 + claims 추출
        AuthDto.TokenResult result = jwtProvider.validateRefreshToken(refreshToken);
        if (result.status() == TokenStatus.INVALID) {
            throw new JwtAuthenticationException(TokenStatus.INVALID);
        }

        // 3. AUTHENTICATED / EXPIRED 일때
        String userLoginNo = result.claims().getSubject();
        tokenService.delete(userLoginNo);

        // 4. 삭제완료 후 쿠키 응답 처리
        Cookie accessCookie = jwtProvider.createCookie(
                ACCESS_COOKIE_NAME,
                "",
                0
        );
        ResponseCookie refreshCookie= jwtProvider.setTokenToCookie(
                REFRESH_COOKIE_NAME,
                "",
                0);

        response.addCookie(accessCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private static UserPrincipal toPrincipal(AuthDto.AuthUser authUser) {
        return new UserPrincipal(authUser.login(), authUser.roles());
    }
}
