package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import java.util.List;



public interface ItemWishService {
    void deleteWish(Long itemNo, String userLoginNo);
    void addWishList(ItemWishDto.ItemWishRequest request);
    List<ItemWishDto.ItemWishResponse> findWishList(String userLoginNo);
}
