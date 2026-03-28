package com.shop.storix.portalapi.common.exception;

import com.shop.storix.portalapi.common.ErrorCode;
import lombok.Getter;

/**
 * Storix 비즈니스 로직 전용 커스텀 예외
 * {@link ErrorCode} 해당 에러 Enum 참조 바람
 */
@Getter
public class StorixException extends RuntimeException {

    private final ErrorCode errorCode;

    public StorixException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
