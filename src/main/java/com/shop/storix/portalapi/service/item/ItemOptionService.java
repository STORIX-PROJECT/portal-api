package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;

public interface ItemOptionService {
    ItemOptionDto.OptionValidResponse validateCombination(ItemOptionDto.ItemOptionRequest request);
}
