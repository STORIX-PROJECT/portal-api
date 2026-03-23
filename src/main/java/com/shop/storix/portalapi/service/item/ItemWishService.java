package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;

public interface ItemWishService {
    void deleteWish(ItemWishDto.DeleteWishRequest request);
}
