package com.shop.storix.portalapi.model.dto.auth.domain;


public record PurchaserProfile(
        String purchaserProfileNo,
        String userLoginNo,
        String nickname,
        String phoneNumber) {}
