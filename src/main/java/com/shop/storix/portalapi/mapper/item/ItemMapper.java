package com.shop.storix.portalapi.mapper.item;


import com.shop.storix.portalapi.model.dto.item.request.ItemSearchDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemDto.ItemSearchResponse> searchItem(ItemSearchDto.ItemSearchRequest request);
}
