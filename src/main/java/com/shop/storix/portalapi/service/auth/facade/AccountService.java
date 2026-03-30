package com.shop.storix.portalapi.service.auth.facade;

import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.service.auth.EmailAuthCodeService;
import com.shop.storix.portalapi.service.auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final EmailAuthCodeService emailAuthCodeService;
    private final LoginService loginService;

    public String findUserId(AuthDto.VerifyMailCodeRequest verifyMailCodeRequest , MailPurpose mailPurpose) {
        emailAuthCodeService.verifyCode(verifyMailCodeRequest,mailPurpose);

        return loginService.findLoginIdByEmail(verifyMailCodeRequest.email());
    }

    public void sendMail(AuthDto.SendMailCodeRequest sendMailCodeRequest, MailPurpose mailPurpose, Locale locale)
    {
        emailAuthCodeService.sendCodeMessage(sendMailCodeRequest,mailPurpose,locale);
    }
}
