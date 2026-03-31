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

    public AuthDto.FindUserIdResponse findUserId(AuthDto.VerifyMailCodeRequest verifyMailCodeRequest , MailPurpose mailPurpose) {
        emailAuthCodeService.verifyCode(verifyMailCodeRequest,mailPurpose);

        String email = verifyMailCodeRequest.email();

        if (loginService.existsOauthByEmail(email)) {
            return AuthDto.FindUserIdResponse.oauth(email);
        }

        return AuthDto.FindUserIdResponse.normal(loginService.findLoginIdByEmail(email));
    }

    public void sendMail(AuthDto.SendMailCodeRequest sendMailCodeRequest, MailPurpose mailPurpose, Locale locale)
    {
        emailAuthCodeService.sendCodeMessage(sendMailCodeRequest,mailPurpose,locale);
    }
}
