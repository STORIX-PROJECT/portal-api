package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemWishMapper;
import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemWishServiceImpl implements ItemWishService {
    private final ItemWishMapper itemWishMapper;

    @Override
    public void addWishList(ItemWishDto.ItemWishRequest request) {
        log.info("Add Wish started - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());

        if (itemWishMapper.existsWish(request)) {
            throw new IllegalArgumentException("이미 찜한 상품입니다.");
        }

        log.info("Add Wish Completed");
        itemWishMapper.addWish(request);
    }

    @Override
    public List<ItemWishDto.ItemWishResponse> findWishList(String userLoginNo) {
        log.info("Find Wish started - userLoginNo : {}",userLoginNo);
        List<ItemWishDto.ItemWishResponse> findWish = itemWishMapper.findWish(userLoginNo);

        log.info("Find wishList completed - wishCount : {}",findWish.size());
        return findWish;
    }
}
