package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemWishMapper {
    boolean existsDeleteWish(@Param("itemNo") Long itemNo, @Param("userLoginNo") String userLoginNo);
    int deleteWish(@Param("itemNo") Long itemNo, @Param("userLoginNo") String userLoginNo);
    boolean existsWish(ItemWishDto.ItemWishRequest request);
    int addWish(ItemWishDto.ItemWishRequest request);
    List<ItemWishDto.ItemWishResponse> findWish(String userLoginNo);
}
