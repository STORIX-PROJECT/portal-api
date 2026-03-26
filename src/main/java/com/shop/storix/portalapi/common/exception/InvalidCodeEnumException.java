package com.shop.storix.portalapi.common.exception;

/**
*
* {@link com.shop.storix.portalapi.config.mybatis.EnumTypeHandler} 전용 Exception
**/
public class InvalidCodeEnumException extends RuntimeException {
    public InvalidCodeEnumException(Class<?> type, String code) {
        super("코드값 [" + code + "]  enum type [" + type.getSimpleName() + "]");
    }
}
