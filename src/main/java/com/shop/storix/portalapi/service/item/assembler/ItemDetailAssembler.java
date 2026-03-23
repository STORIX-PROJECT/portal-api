package com.shop.storix.portalapi.service.item.assembler;

import com.shop.storix.portalapi.model.dto.item.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDetailAssembler {
    public static List<ItemOptionDto.OptionGroupResponse> toGroup(List<ItemOptionDto.ItemDetailOptionResponse> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        flat -> new AbstractMap.SimpleEntry<>(flat.groupNo(), flat.groupName()),
                        LinkedHashMap::new,
                        Collectors.mapping(
                                flat -> new ItemOptionDto.OptionResponse(
                                        flat.optionNo(),
                                        flat.optionName(),
                                        flat.price()
                                ),
                                Collectors.toList()
                        )
                ))
                .entrySet().stream()
                .map(e -> new ItemOptionDto.OptionGroupResponse(
                        e.getKey().getKey(),
                        e.getKey().getValue(),
                        e.getValue()
                ))
                .toList();
    }


    public static ItemDetailDto.ItemDetailWithImgResponse toDetailGroup(
            ItemDetailDto.ItemDetailResponse response, List<String> imgUrls
    ) {
        return new ItemDetailDto.ItemDetailWithImgResponse(
                response.itemNo(),
                response.itemName(),
                response.price(),
                imgUrls,
                response.mfd(),
                response.exp(),
                response.itemDescription(),
                response.itemWeight(),
                response.itemWidth(),
                response.itemLength(),
                response.itemHeight(),
                response.itemRemark()
        );
    }
}