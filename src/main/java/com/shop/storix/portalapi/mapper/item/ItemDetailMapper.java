package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemDetailMapper {
    ItemDetailDto.ItemDetailResponse detailItem(Long itemNo);
    List<String> imgUrls(Long itemNo);
    List<ItemOptionDto.ItemDetailOptionResponse> optionDetail(Long itemNo);
}
