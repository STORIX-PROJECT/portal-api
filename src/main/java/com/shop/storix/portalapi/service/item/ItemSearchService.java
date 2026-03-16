package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.search.CateogrySearchDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import java.util.List;

public interface ItemSearchService {

    List<ItemSearchDto.ItemCategoryResponse> categorySearch(CateogrySearchDto.CategorySearchRequest request);
}