package com.shop.storix.portalapi.common.exception;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러
 * {@link StorixException} 커스텀 에러처리는 해당 클래스 참조 바람.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // StorixException 처리
    @ExceptionHandler(StorixException.class)
    protected ResponseEntity<ApiResponse<Void>> handleStorixException(StorixException e) {
        log.error("StorixException: code={}, message={}", e.getErrorCode().getCode(), e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.fail(
                        errorCode.getCode(),
                        errorCode.getDescription()
                        )
                );
    }

    // @Valid 검증 실패 처리 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Validation failed: {}", e.getMessage());

        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse(ErrorCode.INVALID_INPUT.getDescription());

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ApiResponse.fail(
                        ErrorCode.INVALID_INPUT.getCode(),
                        message
                        )
                );
    }

    // 필수 요청 파라미터 누락 처리 핸들러
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.error("Missing parameter: {}", e.getParameterName());

        String message = "필수 파라미터 '" + e.getParameterName() + "'이(가) 누락되었습니다.";

        return ResponseEntity
                .status(ErrorCode.MISSING_PARAMETER.getHttpStatus())
                .body(ApiResponse.fail(
                        ErrorCode.MISSING_PARAMETER.getCode(),
                        message
                        )
                );
    }

    // 잘못된 JSON 형식 처리 핸들러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ApiResponse.fail(
                        ErrorCode.INVALID_INPUT.getCode(),
                        ErrorCode.INVALID_INPUT.getDescription()
                        )
                );
    }

    // 지원하지 않는 HTTP 메서드 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("Method not supported: {}", e.getMethod());

        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus())
                .body(ApiResponse.fail(
                        ErrorCode.METHOD_NOT_ALLOWED.getCode(),
                        ErrorCode.METHOD_NOT_ALLOWED.getDescription()
                        )
                );
    }

    /**
     * 그 외 모든 예외 처리 핸들러
     *  - fallback 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled exception occurred", e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.fail(
                        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        ErrorCode.INTERNAL_SERVER_ERROR.getDescription()
                        )
                );
    }
}
