package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ItemWishMapper {
    boolean existsDeleteWish(Long itemNo, String userLoginNo);
    int deleteWish(Long itemNo, String userLoginNo);
    boolean existsWish(ItemWishDto.ItemWishRequest request);
    int addWish(ItemWishDto.ItemWishRequest request);
    List<ItemWishDto.ItemWishResponse> findWish(String userLoginNo);
}
