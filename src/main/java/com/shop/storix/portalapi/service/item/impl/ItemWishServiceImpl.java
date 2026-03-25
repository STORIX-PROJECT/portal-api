package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemWishMapper;
import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
