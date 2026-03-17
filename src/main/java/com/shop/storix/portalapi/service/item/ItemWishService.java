package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.request.wish.ItemWishRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.wish.ItemWishResponseDto;

import java.util.List;

public interface ItemWishService {
    void addWishList(ItemWishRequestDto.WishRequest request);
    List<ItemWishResponseDto.ItemWishResponse> findWishList(String userLoginNo);
}
