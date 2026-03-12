package com.shop.storix.portalapi.model.dto.auth;

import com.shop.storix.portalapi.common.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus implements CodeEnum {
    ACTIVE("ACTIVE", "정상"),
    LOCKED("LOCKED", "잠금"),
    INACTIVE("INACTIVE", "비활성화");

    private final String code;
    private final String description;
}
