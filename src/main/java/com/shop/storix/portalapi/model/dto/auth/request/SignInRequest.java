package com.shop.storix.portalapi.model.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank String id,
        @NotBlank String pw) {
}
