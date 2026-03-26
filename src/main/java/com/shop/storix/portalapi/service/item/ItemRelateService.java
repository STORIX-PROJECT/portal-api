package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import java.util.List;

public interface ItemRelateService {
    List<RelateItemDto.RelateItemResponse> relateItem(Long itemNo);
}
