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
    public void deleteWish(ItemWishDto.DeleteWishRequest request) {
        log.info("Delete Wish started - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());

            if (!itemWishMapper.existsWish(request)) {
                log.info("Delete Wish Not found - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());
                throw new IllegalArgumentException("위시가 존재하지 않습니다.");
            }

            int deleteCount = itemWishMapper.deleteWish(request);

            if (deleteCount == 0) {
                log.error("Delete Fail - itemNo : {}",request.itemNo());
                throw new IllegalArgumentException("위시 삭제에 실패했습니다.");
            }
            log.info("Delete Wish Completed - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());

    }

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
