package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailOptionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    ItemDetailDto.ItemDetailResponse detailItem(Long itemNo);
    List<String> imgUrls(Long itemNo);
//    List<ItemDetailOptionDto.ItemDetailOptionResponse> optionDetail(Long itemNo);
}
