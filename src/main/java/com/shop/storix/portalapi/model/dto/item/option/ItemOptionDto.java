package com.shop.storix.portalapi.model.dto.item.option;

import org.apache.ibatis.type.Alias;

import java.util.List;

public class ItemOptionDto {

    // 내부용
    @Alias("ItemDetailOptionResponse")
    public record ItemDetailOptionResponse (
            Long groupNo,
            String groupName,
            Long optionNo,
            String optionName,
            Integer price
    ) {}

    // 이거로 응답
    public record OptionGroupResponse (
            Long groupNo,
            String groupName,
            List<ItemOptionDto.OptionResponse> options
    ) {}


    public record OptionResponse (
            Long optionNo,
            String optionName,
            Integer price
    ) {}
}
