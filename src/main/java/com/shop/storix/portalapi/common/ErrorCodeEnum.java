package com.shop.storix.portalapi.common;

import org.springframework.http.HttpStatus;

public interface ErrorCodeEnum extends CodeEnum{
    HttpStatus getHttpStatus();
}
