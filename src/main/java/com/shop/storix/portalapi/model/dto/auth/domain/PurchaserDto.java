package com.shop.storix.portalapi.model.dto.auth.domain;

public class PurchaserDto {
    public PurchaserDto() {}

    public record Purchaser(
            String purchaserProfileNo,
            String userLoginNo,
            String nickname,
            String phoneNumber) {
    }
}