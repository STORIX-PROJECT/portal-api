package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailOptionDto;

import java.util.List;

public interface ItemDetailService {
    ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo);
    List<ItemDetailOptionDto.OptionGroupResponse> detailOption(Long itemNo);

}
