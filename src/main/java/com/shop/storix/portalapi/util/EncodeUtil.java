package com.shop.storix.portalapi.util;

import java.util.Base64;

public class EncodeUtil {
    public static String encodeToBase64(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
