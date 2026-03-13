package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.request.ItemWishRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.wish.ItemWishResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    boolean existsWish(ItemWishRequestDto.WishRequest request);
    void addWish(ItemWishRequestDto.WishRequest request);
    List<ItemWishResponseDto.ItemWishResponse> findWish(String userLoginNo);
}
