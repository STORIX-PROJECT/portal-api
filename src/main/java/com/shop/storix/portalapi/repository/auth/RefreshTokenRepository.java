package com.shop.storix.portalapi.repository.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "REFRESH:";
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public void save(String userLoginNo, String token) {
        redisTemplate.opsForValue()
                .set(PREFIX + userLoginNo, token, Duration.ofMillis(refreshExpiration));
    }

    public Optional<AuthDto.Token> findByUserLoginNo(String userLoginNo) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(PREFIX + userLoginNo))
                .map(refreshToken -> new AuthDto.Token(userLoginNo,refreshToken)
        );
    }

    public void deleteByUserLoginNo(String userLoginNo) {
        redisTemplate.delete(PREFIX + userLoginNo);
    }
}
