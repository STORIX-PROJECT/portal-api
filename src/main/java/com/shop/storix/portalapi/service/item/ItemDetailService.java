package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;

import java.util.List;

public interface ItemDetailService {
    ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo);
    List<ItemOptionDto.OptionGroupResponse> detailOption(Long itemNo);
}
