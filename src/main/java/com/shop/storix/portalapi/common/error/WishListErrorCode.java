package com.shop.storix.portalapi.common.error;

import com.shop.storix.portalapi.common.ErrorCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WishListErrorCode implements ErrorCodeEnum {

    // ========== WIS: 위시리스트 ==========
    WISH_ALREADY_EXISTS(HttpStatus.CONFLICT, "WIS:001", "이미 찜한 상품입니다."),
    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "WIS:002", "위시리스트에 해당 상품이 없습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
