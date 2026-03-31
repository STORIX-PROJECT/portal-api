package com.shop.storix.portalapi.common.error;

import com.shop.storix.portalapi.common.ErrorCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MailErrorCode implements ErrorCodeEnum {

    // ========== MAI: 메일   ==========
    MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAI:001", "이메일 발송에 실패했습니다."),
    MAIL_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "MAI:002", "인증 코드가 만료되었습니다."),
    MAIL_CODE_INVALID(HttpStatus.BAD_REQUEST, "MAI:003", "인증 코드가 올바르지 않습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
