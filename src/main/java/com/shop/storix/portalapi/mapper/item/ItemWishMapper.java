package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemWishRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemWishMapper {
    boolean existsWish(ItemWishRequestDto.DeleteWishRequest request);
    void deleteWish(ItemWishRequestDto.DeleteWishRequest request);
}
