package com.shop.storix.portalapi.config.common;

import com.shop.storix.portalapi.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("비즈니스 로직 에러 발생: {}", e.getMessage());
        return ApiResponse.fail("400", e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleAllException(Exception e) {
        log.error("서버 에러: ", e);
        return ApiResponse.fail("500", "서버 오류가 발생했습니다.", null);
    }
}