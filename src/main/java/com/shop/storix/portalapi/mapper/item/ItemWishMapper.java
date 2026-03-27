package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemWishMapper {
    int deleteWish(@Param("itemNo") Long itemNo, @Param("userLoginNo") String userLoginNo);
    boolean existsWish(@Param("itemNo") Long itemNo, @Param("userLoginNo") String userLoginNo);
    int addWish(@Param("itemNo") Long itemNo, @Param("userLoginNo") String userLoginNo, @Param("createdDt") String createdDt);
    List<ItemWishDto.ItemWishResponse> findWish(@Param("userLoginNo") String userLoginNo);
}
