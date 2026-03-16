package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemSearchDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemDto;


import java.util.List;

public interface ItemSearchService {
    List<ItemDto.ItemSearchResponse> searchItem(ItemSearchDto.ItemSearchRequest request);
}