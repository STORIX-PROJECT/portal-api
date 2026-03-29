package com.shop.storix.portalapi.common.exception;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.common.ErrorCodeEnum;
import com.shop.storix.portalapi.common.error.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

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
        ErrorCodeEnum errorCodeEnum = e.getErrorCodeEnum();
        String message = e.getMessage();

        if (errorCodeEnum.getHttpStatus().is5xxServerError()) {
            log.error("StorixException: code={}, message={}", errorCodeEnum.getCode(), message);
        } else {
            /* if errorCode.getHttpStatus().is4xxClientError() */
            log.warn("StorixException: code={}, message={}", errorCodeEnum.getCode(), message);
        }
        return ResponseEntity
                .status(errorCodeEnum.getHttpStatus())
                .body(ApiResponse.fail(
                        errorCodeEnum.getCode(),
                        errorCodeEnum.getDescription()
                ));
    }

    // @Valid 검증 실패 처리 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = Optional.of(e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "))
                )
                .filter(msg -> !msg.isBlank())
                .orElse(CommonErrorCode.INVALID_INPUT.getDescription());

        log.warn("Validation failed: {}", message);

        return ResponseEntity
                .status(CommonErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.INVALID_INPUT.getCode(),
                        message
                ));
    }

    // 필수 요청 파라미터 누락 처리 핸들러
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("Missing parameter: {}", e.getParameterName());

        String message = "필수 파라미터 '" + e.getParameterName() + "'이(가) 누락되었습니다.";

        return ResponseEntity
                .status(CommonErrorCode.MISSING_PARAMETER.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.MISSING_PARAMETER.getCode(),
                        message
                ));
    }

    // 잘못된 JSON 형식 처리 핸들러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("Message Not Readable : {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());

        return ResponseEntity
                .status(CommonErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.INVALID_INPUT.getCode(),
                        CommonErrorCode.INVALID_INPUT.getDescription()
                ));
    }

    // 지원하지 않는 HTTP 메서드 처리 핸들러
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("Method not supported: {}", e.getMethod());

        return ResponseEntity
                .status(CommonErrorCode.METHOD_NOT_ALLOWED.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.METHOD_NOT_ALLOWED.getCode(),
                        CommonErrorCode.METHOD_NOT_ALLOWED.getDescription()
                ));
    }

    // 존재하지 않는 URL 요청 처리 핸들러
    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException e) {
        log.warn("No Resource Found: {}", e.getMessage());

        return ResponseEntity
                .status(CommonErrorCode.RESOURCE_NOT_FOUND.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.RESOURCE_NOT_FOUND.getCode(),
                        CommonErrorCode.RESOURCE_NOT_FOUND.getDescription()
                ));
    }

    // 지원하지 않는 Content-Type 요청
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.warn("Media Type NotSupported : {}", e.getMessage());
        return ResponseEntity
                .status(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode(),
                        CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getDescription()
                ));
    }

    /**
     * 그 외 모든 예외 처리 핸들러
     *  - fallback 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled exception occurred", e);

        return ResponseEntity
                .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.fail(
                        CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        CommonErrorCode.INTERNAL_SERVER_ERROR.getDescription()
                        )
                );
    }
}
