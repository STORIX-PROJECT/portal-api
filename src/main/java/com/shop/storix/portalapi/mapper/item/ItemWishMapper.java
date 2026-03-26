package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ItemWishMapper {
    boolean existsWish(ItemWishDto.DeleteWishRequest request);
    int deleteWish(ItemWishDto.DeleteWishRequest request);
    boolean existsWish(ItemWishDto.ItemWishRequest request);
    void addWish(ItemWishDto.ItemWishRequest request);
    List<ItemWishDto.ItemWishResponse> findWish(@Param("userLoginNo") String userLoginNo);
}
