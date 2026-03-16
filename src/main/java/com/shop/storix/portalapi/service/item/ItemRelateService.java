package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.response.relate.RelateItemDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemRelateService {
    List<RelateItemDto.RelateItemResponse> relateItem(@Param("itemNo") Long itemNo);
}
