package com.shop.storix.portalapi.config.auth.handler;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgument(IllegalArgumentException e) {
        return ApiResponse.fail(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    }

}
