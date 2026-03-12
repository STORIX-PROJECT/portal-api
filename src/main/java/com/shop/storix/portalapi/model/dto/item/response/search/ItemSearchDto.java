package com.shop.storix.portalapi.model.dto.item.response.search;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemSearchDto {
    public record ItemCategoryResponse (
            Long itemNo,
            String itemName,
            Integer price,
            String itemStatus,
            String imgUrl,
            boolean isNew,
            Integer orderCount,
            Integer reviewCount
    ) {}
}
