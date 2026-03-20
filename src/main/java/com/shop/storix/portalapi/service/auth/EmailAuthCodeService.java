package com.shop.storix.portalapi.service.auth;

import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;

public interface EmailAuthCodeService {
    void sendCodeMessage(AuthDto.SendMailCodeRequest sendMailCodeRequest, MailPurpose mailPurpose);
    void verifyCode(AuthDto.VerifyMailCodeRequest verifyMailCodeRequest, MailPurpose mailPurpose);
}
