package com.shop.storix.portalapi.common.error;

import com.shop.storix.portalapi.common.ErrorCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCodeEnum {

    // ========== LOG: 로그인  ==========
    LOGIN_NOT_FOUND(HttpStatus.NOT_FOUND, "LOG:001", "해당 로그인 계정을 찾을 수 없습니다."),
    OAUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "LOG:002", "해당 OAuth 로그인 계정을 찾을 수 없습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
