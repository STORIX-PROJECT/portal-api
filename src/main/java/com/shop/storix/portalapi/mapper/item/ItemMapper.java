package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ItemMapper {

    List<ItemSearchDto.ItemCategoryResponse> searchCategory(CategorySearchRequest request);
}
