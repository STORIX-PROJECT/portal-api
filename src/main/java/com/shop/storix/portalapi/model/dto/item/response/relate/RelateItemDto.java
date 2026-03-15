package com.shop.storix.portalapi.model.dto.item.response.relate;

public class RelateItemDto {
    public record RelateItemResponse (
            Long itemNo,
            String itemName,
            Integer price,
            String itemStatus,
            String imgUrl,
            Integer orderCount
    ) {}
}
