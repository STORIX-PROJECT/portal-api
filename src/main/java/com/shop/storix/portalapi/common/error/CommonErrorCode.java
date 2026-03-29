package com.shop.storix.portalapi.common.error;

import com.shop.storix.portalapi.common.ErrorCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 규칙: 도메인명 앞 3자리 + ":" + 순차 번호
 *
 * HttpStatus Code , Error Code , description
 * 설명은 간단하게 한줄로 작성
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCodeEnum {

    // ========== COM: 공통 ==========
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COM:001", "잘못된 입력입니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "COM:002", "필수 파라미터가 누락되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COM:003", "서버 내부 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COM:004", "지원하지 않는 HTTP 메서드입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COM:005", "요청한 리소스를 찾을 수 없습니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "COM:006", "지원하지 않는 Content-Type입니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
