package com.shop.storix.portalapi.model.dto.item.response.wish;

public class ItemWishResponseDto {
    public record ItemWishResponse (
            String userLoginNo, //USER_LOGIN DB
            Long itemNo, // ITEM DB
            String itemName, // ITEM DB
            String createdDt // ITEM_WISH DB
    ) {}
}
