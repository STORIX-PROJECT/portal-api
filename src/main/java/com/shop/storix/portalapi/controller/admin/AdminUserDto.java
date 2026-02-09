package com.shop.storix.portalapi.controller.admin;

import lombok.Builder;

@Builder
public record AdminUserDto(
        Long id,
        String username,
        String email,
        String role,
        String createdAt
) {
}