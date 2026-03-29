package com.shop.storix.portalapi.common.exception;

import com.shop.storix.portalapi.common.ErrorCodeEnum;
import com.shop.storix.portalapi.common.error.CommonErrorCode;
import lombok.Getter;

/**
 * Storix 비즈니스 로직 전용 커스텀 예외
 * {@link CommonErrorCode} 해당 에러 Enum 참조 바람
 */
@Getter
public class StorixException extends RuntimeException {

    private final ErrorCodeEnum errorCodeEnum;

    public StorixException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getDescription());
        this.errorCodeEnum = errorCodeEnum;
    }
}
