package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemWishMapper;
import com.shop.storix.portalapi.model.dto.item.request.wish.ItemWishRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.wish.ItemWishResponseDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemWishServiceImpl implements ItemWishService {
    private final ItemWishMapper itemWishMapper;

    @Override
    public void addWishList(ItemWishRequestDto.WishRequest request) {
        log.info("Add Wish started - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());
        try {
           if(request.itemNo() == null || request.userLoginNo() == null) {
               log.warn("WishList failed");
               throw new IllegalArgumentException("공백입니다.");
           }

           if (itemWishMapper.existsWish(request)) {
               log.warn("Wishlist add failed - already exists");
               throw new IllegalArgumentException("이미 찜한 상품입니다.");
           }

           log.info("Add Wish Completed");
            itemWishMapper.addWish(request);

       } catch (IllegalArgumentException e) {
           log.warn("Add Wish failed - {}",e.getMessage());
           throw e;
       } catch (Exception e) {
           log.warn("Add Wish error",e);
           throw e;
       }
    }

    @Override
    public List<ItemWishResponseDto.ItemWishResponse> findWishList(String userLoginNo) {
        log.info("Find Wish started - userLoginNo : {}",userLoginNo);

        List<ItemWishResponseDto.ItemWishResponse> findWish = itemWishMapper.findWish(userLoginNo);
        try {
            if(CollectionUtils.isEmpty(findWish)) {
                log.warn("Not wish List");
                throw new IllegalArgumentException("위시리스트가 없습니다.");
            }

            log.info("Find wishList completed - wishCount : {}",findWish.size());
            return findWish;

        } catch (IllegalArgumentException e) {
            log.warn("Find Wish failed - {}",e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("Find Wish error" ,e);
            throw e;
        }
    }
}
