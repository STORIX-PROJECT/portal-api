package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemWishMapper {
    boolean existsWish(ItemWishDto.DeleteWishRequest request);
    void deleteWish(ItemWishDto.DeleteWishRequest request);
}
