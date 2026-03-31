package com.shop.storix.portalapi.common.mail;

import com.shop.storix.portalapi.common.error.MailErrorCode;
import com.shop.storix.portalapi.common.exception.StorixException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * mailSender
 * {@link MailVariables} 해당 인터페이스 구현체에 따라 별개 메시지 발송
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Getter
public class MailSender {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void send(String to, MailVariables variables, Locale locale) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(mailContentBuilder.buildSubject(variables, locale));
            helper.setText(mailContentBuilder.buildContent(variables, locale), true);

            javaMailSender.send(message);
            log.info("메일 발송 완료 - to: {}, template: {}", to, variables.getTemplateName());
        } catch (MessagingException e) {
            log.error("메일 발송 실패 - to: {}, template: {}", to, variables.getTemplateName(), e);
            throw new StorixException(MailErrorCode.MAIL_SEND_FAILED);
        }
    }
}