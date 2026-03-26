package com.shop.storix.portalapi.service.item.assembler;

import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import java.util.List;

public class ItemRelateAssembler {
    private ItemRelateAssembler () {}
    public static List<RelateItemDto.RelateItemResponse> toRelateGroup(
            List<RelateItemDto.RelateDto> response
    ) {
        return response.stream()
                .map(relate -> RelateItemDto.RelateItemResponse.builder()
                        .itemNo(relate.itemNo())
                        .itemName(relate.itemName())
                        .price(relate.price())
                        .imgUrl(relate.imgUrl())
                        .orderCount(relate.orderCount())
                        .build())
                .toList();
        }
    }
