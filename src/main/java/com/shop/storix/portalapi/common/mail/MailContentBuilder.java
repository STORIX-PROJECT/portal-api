package com.shop.storix.portalapi.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;

    /*내용 생성*/
    public String buildContent(MailVariables variables, Locale locale) {
        Context context = new Context(locale);
        context.setVariables(variables.toMap());
        return templateEngine.process(variables.getTemplateName(), context);
    }

    /*제목 생성*/
    public String buildSubject(MailVariables variables, Locale locale) {
        return messageSource.getMessage(variables.getSubjectKey(), null, locale);
    }
}
