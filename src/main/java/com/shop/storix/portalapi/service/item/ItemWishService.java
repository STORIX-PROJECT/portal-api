package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemWishRequestDto;

public interface ItemWishService {
    void deleteWish(ItemWishRequestDto.DeleteWishRequest request);
}
