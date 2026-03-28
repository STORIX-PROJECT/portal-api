package com.shop.storix.portalapi.model.dto.purchaser.domain;

import jakarta.annotation.Nullable;

public class PurchaserDto {
    public PurchaserDto() {}

    public record Purchaser(
            String purchaserProfileNo,
            String userLoginNo,
            @Nullable String nickname,
            @Nullable String phoneNumber,
            String email) {
    }
}