package com.shop.storix.portalapi.service.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Param;
import java.util.List;



public interface ItemWishService {
    void deleteWish(ItemWishDto.DeleteWishRequest request);
    void addWishList(ItemWishDto.ItemWishRequest request);
    List<ItemWishDto.ItemWishResponse> findWishList(@Param("userLoginNo") String userLoginNo);
}
