package com.shop.storix.portalapi.model.dto.item.request.wish;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemWishRequestDto {

    public record WishRequest (
            String userLoginNo,
            Long itemNo,
            String createdDt
    ) {}
}
