package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.OptionValidRequest;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.option.ItemOptionDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;


import java.util.List;

public interface ItemSearchService {
    List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request);

//    List<ItemSearchDto.ItemSearchResponse> categorySearch(CategorySearchRequest request);
//
//    ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo);
//
//    List<ItemOptionDto.ItemOptionResponse> optionItem(Long itemNo);
//
//    ItemOptionDto.OptionCombiResponse optionDetailItem(OptionValidRequest.OptionDetailRequest request);
}