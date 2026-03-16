package com.shop.storix.portalapi.mapper.item;


import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchResponseDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemSearchResponseDto.ItemSearchResponse> searchItem(ItemSearchRequestDto.ItemSearchRequest request);
}
