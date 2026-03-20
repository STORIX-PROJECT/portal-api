package com.shop.storix.portalapi.config.common;


import com.shop.storix.portalapi.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Invalid Option : {}", e.getMessage());

        return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleAllException(Exception e) {
        log.error("Server error :  ", e);
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.", null);
    }
}