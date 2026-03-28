package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.util.EncodeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;

@Component
public class JwtUtils {
    public AuthDto.TokenResult getTokenStatus(String token, Key secretKey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new AuthDto.TokenResult(TokenStatus.AUTHENTICATED, claims);
        } catch (ExpiredJwtException e) {
            return new AuthDto.TokenResult(TokenStatus.EXPIRED, e.getClaims());
        } catch (JwtException e) {
            return new AuthDto.TokenResult(TokenStatus.INVALID, null);
        }
    }

    public String resolveFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            return null;
        }

        String token = header.substring(7).trim();
        return StringUtils.hasText(token) ? token : null;
    }

    public String resolveFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;

        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .map(value -> value == null ? null : value.trim())
                .filter(value -> value != null && !value.isEmpty())
                .findFirst()
                .orElse(null);
    }

    public Key getSigningKey(String secretKey) {
        String encodedKey = EncodeUtil.encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims parseClaims(String token, Key secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
