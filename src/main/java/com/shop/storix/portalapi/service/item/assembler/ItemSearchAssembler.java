package com.shop.storix.portalapi.service.item.assembler;

import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;

import java.util.List;

public class ItemSearchAssembler {
    public static List<ItemSearchDto.ControllerResponse> toSearchGroup(
            List<ItemSearchDto.ItemSearchResponse> response
    ) {
        return response.stream()
                .map(search -> ItemSearchDto.ControllerResponse.builder()
                        .itemNo(search.itemNo())
                        .itemName(search.itemName())
                        .price(search.price())
                        .itemStatus(search.itemStatus())
                        .imgUrl(search.imgUrl())
                        .isNew(search.isNew())
                        .orderCount(search.orderCount())
                        .reviewCount(search.reviewCount())
                        .build())
                .toList();
    }
    private ItemSearchAssembler() {}
}