package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;

import java.util.Locale;

public interface EmailAuthCodeService {
    void sendCodeMessage(AuthDto.SendMailCodeRequest sendMailCodeRequest, MailPurpose mailPurpose, Locale locale);
    void verifyCode(AuthDto.VerifyMailCodeRequest verifyMailCodeRequest, MailPurpose mailPurpose);
}
