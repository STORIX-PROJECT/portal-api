package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.common.error.MailErrorCode;
import com.shop.storix.portalapi.common.exception.StorixException;
import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.repository.auth.EmailAuthCodeRepository;
import com.shop.storix.portalapi.service.auth.EmailAuthCodeService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthCodeServiceImpl implements EmailAuthCodeService {
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String senderEmail;

    @Override
    public void sendCodeMessage(AuthDto.SendMailCodeRequest sendMailCodeRequest , MailPurpose mailPurpose) {
        emailAuthCodeRepository.deleteCode(sendMailCodeRequest.email(),mailPurpose);

        AuthDto.EmailAuthCode emailAuthCode = new AuthDto.EmailAuthCode(sendMailCodeRequest.email(),generateCode(),mailPurpose);
        emailAuthCodeRepository.saveCode(emailAuthCode);

        sendMail(emailAuthCode);
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

    // 메일 보내기
    private void sendMail(AuthDto.EmailAuthCode emailAuthCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(emailAuthCode.email());
            helper.setSubject("[Storix] 이메일 인증 코드");
            helper.setText(buildMailContent(emailAuthCode.code()), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new StorixException(MailErrorCode.MAIL_SEND_FAILED);
        }
    }

    // 메일 내용 만들기
    private String buildMailContent(String code) {
        return """
                <div style="max-width:500px; margin:0 auto; padding:20px; font-family:sans-serif;">
                    <h2>이메일 인증 코드</h2>
                    <p>아래 인증 코드를 입력해주세요.</p>
                    <div style="font-size:32px; font-weight:bold; letter-spacing:8px;
                                padding:20px; background:#f5f5f5; text-align:center;">
                        %s
                    </div>
                    <p style="color:#999; font-size:12px;">
                        인증 코드는 5분간 유효합니다.
                    </p>
                </div>
                """.formatted(code);
    }
}
