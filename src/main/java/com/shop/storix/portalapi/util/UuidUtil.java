package com.shop.storix.portalapi.util;

import java.util.UUID;

public final class UuidUtil {
    /**
     * RFC 4122 표준 UUID (하이픈 포함, 36자)
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 하이픈 제거 UUID (32자)
     */
    public static String randomUuidWithoutHyphen() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
