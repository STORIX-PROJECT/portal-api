package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;

import java.util.List;

public interface ItemSearchService {
    List<ItemSearchDto.ItemCategoryResponse> categorySearch(ItemSearchDto.CategorySearchRequest request);
}