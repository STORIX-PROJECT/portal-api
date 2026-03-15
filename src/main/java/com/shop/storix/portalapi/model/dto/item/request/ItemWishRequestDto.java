package com.shop.storix.portalapi.model.dto.item.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemWishRequestDto {

    public record DeleteWishRequest (
            String userLoginNo,
            Long itemNo
    ) {}
}
