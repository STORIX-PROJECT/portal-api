package com.shop.storix.portalapi.controller.admin;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String success,
        String code,
        String message,
        T data
) {
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success("true")
                .code("200")
                .message("Request Successful")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> fail(String statusCode, String message, T data) {
        return ApiResponse.<T>builder()
                .success("false")
                .code(statusCode)
                .message(message)
                .data(data)
                .build();
    }
}