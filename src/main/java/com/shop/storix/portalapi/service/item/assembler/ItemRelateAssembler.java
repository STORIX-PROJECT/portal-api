package com.shop.storix.portalapi.service.item.assembler;

import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import java.util.List;

public class ItemRelateAssembler {
    public static List<RelateItemDto.ControllerResponse> toRelateGroup(
            List<RelateItemDto.RelateResponse> response
    ) {
        return response.stream()
                .map(relate -> RelateItemDto.ControllerResponse.builder()
                        .itemNo(relate.itemNo())
                        .itemName(relate.itemName())
                        .itemStatus(relate.itemStatus())
                        .price(relate.price())
                        .imgUrl(relate.imgUrl())
                        .orderCount(relate.orderCount())
                        .build())
                .toList();
        }
    }
