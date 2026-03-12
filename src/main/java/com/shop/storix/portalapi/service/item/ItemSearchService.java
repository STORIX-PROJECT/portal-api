package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;


import java.util.List;

public interface ItemSearchService {
    List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request);
}