package com.shop.storix.portalapi.model.dto.item.wish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemWishDto {
    public record DeleteWishRequest (
            @NotBlank
            String userLoginNo,
            @NotNull
            Long itemNo
    ) {}
}
