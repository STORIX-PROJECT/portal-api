package com.shop.storix.portalapi.model.dto.item.response.detail;

import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
public class ItemDetailOptionDto {

    public record ItemDetailOptionResponse (
            Long groupNo,
            String groupName,
            Long optionNo,
            String optionName,
            Integer price
    ) {}

    // 이거로 응답받아야함
    public record OptionGroupResponse (
            Long groupNo,
            String groupName,
            List<OptionResponse> options
    ) {}

    public record OptionResponse (
            Long optionNo,
            String optionName,
            Integer price
    ) {}
}
