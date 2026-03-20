package com.shop.storix.portalapi.repository.auth;

import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailAuthCodeRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CODE_PREFIX     = "EMAIL:CODE:";
    private static final String VERIFIED_PREFIX = "EMAIL:VERIFIED:";

    @Value("${redis.email-code.expiration}")
    private long codeTtl;

    public void saveCode(AuthDto.EmailAuthCode emailAuthCode) {
        redisTemplate.opsForValue()
                .set(makeCode(CODE_PREFIX,emailAuthCode.mailPurpose()) + emailAuthCode.email(),
                        emailAuthCode.code(),
                        Duration.ofMinutes(codeTtl));
    }

    public Optional<AuthDto.EmailAuthCode> findCode(String email , MailPurpose mailPurpose) {
        return Optional.ofNullable(
                        redisTemplate.opsForValue().get(makeCode(CODE_PREFIX,mailPurpose)+ email))
                .map(code -> new AuthDto.EmailAuthCode(email, code ,mailPurpose));
    }

    public void deleteCode(String email ,MailPurpose mailPurpose) {
        redisTemplate.delete(makeCode(CODE_PREFIX,mailPurpose) + email);
    }

    private String makeCode(String prefix, MailPurpose mailPurpose) {
        return prefix + mailPurpose.name() + ":";
    }
}
