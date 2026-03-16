package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchResponseDto;


import java.util.List;

public interface ItemSearchService {
    List<ItemSearchResponseDto.ItemSearchResponse> searchItem(ItemSearchRequestDto.ItemSearchRequest request);
}