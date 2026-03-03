package com.shop.storix.portalapi.service.auth.facade;

import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.service.auth.JwtGenerator;
import com.shop.storix.portalapi.service.auth.JwtUtils;
import com.shop.storix.portalapi.service.auth.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.security.Key;
import java.util.List;

@Service
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final JwtUtils jwtUtil;
    private final TokenService tokenService;

    private final Key ACCESS_SECRET_KEY;
    private final Key REFRESH_SECRET_KEY;

    private final long ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    public JwtProvider(
            TokenService tokenService,
            JwtGenerator jwtGenerator, JwtUtils jwtUtil,
            @Value("${jwt.access-secret}") String ACCESS_SECRET_KEY,
            @Value("${jwt.refresh-secret}") String REFRESH_SECRET_KEY,
            @Value("${jwt.access-expiration}") long ACCESS_EXPIRATION,
            @Value("${jwt.refresh-expiration}") long REFRESH_EXPIRATION) {
        this.tokenService = tokenService;
        this.jwtGenerator = jwtGenerator;
        this.jwtUtil = jwtUtil;
        this.ACCESS_SECRET_KEY = jwtUtil.getSigningKey(ACCESS_SECRET_KEY);
        this.REFRESH_SECRET_KEY = jwtUtil.getSigningKey(REFRESH_SECRET_KEY);
        this.ACCESS_EXPIRATION = ACCESS_EXPIRATION;
        this.REFRESH_EXPIRATION = REFRESH_EXPIRATION;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return jwtGenerator.generateAccessToken(ACCESS_SECRET_KEY, ACCESS_EXPIRATION, userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String refreshToken = jwtGenerator.generateRefreshToken(REFRESH_SECRET_KEY, REFRESH_EXPIRATION, userDetails);
        tokenService.save(new AuthDto.Token(userDetails.getUsername(), refreshToken));
        return refreshToken;
    }

    public TokenStatus validateToken(String token) {
        return jwtUtil.getTokenStatus(token, ACCESS_SECRET_KEY);
    }

    public TokenStatus validateRefreshToken(String token) {
        return jwtUtil.getTokenStatus(token, REFRESH_SECRET_KEY);
    }

    public String resolveAccessToken(HttpServletRequest req) {
        return jwtUtil.resolveFromHeader(req);
    }

    public String resolveRefreshToken(HttpServletRequest req, String cookieName) {
        return jwtUtil.resolveFromCookie(req,cookieName);
    }

    public Claims parseRefreshClaims (String refreshToken) {
        return jwtUtil.parseClaims(refreshToken,REFRESH_SECRET_KEY);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = jwtUtil.parseClaims(accessToken, ACCESS_SECRET_KEY);

        String id = claims.get("id", String.class);
        if (!StringUtils.hasText(id)) {
            return null;
        }

        List<String> roles = claims.get("roles", List.class);

        List<SimpleGrantedAuthority> authorities;
        if (roles == null) {
            authorities = List.of();
        } else {
            authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }

        AuthDto.JwtPrincipal principal =
                new AuthDto.JwtPrincipal(id, roles == null ? List.of() : roles);

        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                authorities
        );
    }

    // accessToken
    public Cookie createCookie(String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
       // cookie.setSecure(true);       // HTTPS 설정
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        return cookie;
    }
    // refreshToken
    public ResponseCookie setTokenToCookie(String tokenPrefix, String token, long maxAgeSeconds) {
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(true)
                .sameSite("Strict")
                //.secure(true)
                .build();
    }
}
