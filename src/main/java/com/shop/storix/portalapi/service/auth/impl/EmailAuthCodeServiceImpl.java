package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.common.error.MailErrorCode;
import com.shop.storix.portalapi.common.exception.StorixException;
import com.shop.storix.portalapi.common.mail.MailSender;
import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.repository.auth.EmailAuthCodeRepository;
import com.shop.storix.portalapi.service.auth.EmailAuthCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthCodeServiceImpl implements EmailAuthCodeService {
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final MailSender mailSender;

    @Value("${redis.email-code.expiration-minutes}")
    private int expirationMinutes;

    @Override
    public void sendCodeMessage(AuthDto.SendMailCodeRequest sendMailCodeRequest ,
                                MailPurpose mailPurpose ,
                                Locale locale) {
        emailAuthCodeRepository.deleteCode(sendMailCodeRequest.email(),mailPurpose);

        AuthDto.EmailAuthCode emailAuthCode = new AuthDto.EmailAuthCode(sendMailCodeRequest.email(),generateCode(),mailPurpose);
        emailAuthCodeRepository.saveCode(emailAuthCode);

        // 메일 발송
        mailSender.send(
                sendMailCodeRequest.email(),
                new AuthDto.AuthCodeVariables(emailAuthCode.code(), expirationMinutes),
                locale
        );
    }

    @Override
    public void verifyCode(AuthDto.VerifyMailCodeRequest verifyMailCodeRequest, MailPurpose mailPurpose) {
        AuthDto.EmailAuthCode storedCode = emailAuthCodeRepository.findCode(verifyMailCodeRequest.email(),mailPurpose)
                .orElseThrow(() -> new StorixException(MailErrorCode.MAIL_CODE_EXPIRED));

        if (!storedCode.code().equals(verifyMailCodeRequest.code())) {
            throw new StorixException(MailErrorCode.MAIL_CODE_INVALID);
        }

        emailAuthCodeRepository.deleteCode(verifyMailCodeRequest.email(),mailPurpose);
    }

    // 랜덤 난수 생성
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1_000_000));
    }
}
