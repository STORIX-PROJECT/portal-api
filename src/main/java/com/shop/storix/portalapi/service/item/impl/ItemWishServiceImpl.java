package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.common.ErrorCode;
import com.shop.storix.portalapi.common.exception.StorixException;
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
    public void deleteWish(Long itemNo, String userLoginNo) {
        log.info("Delete Wish started - itemNo : {}, userLoginNo : {}", itemNo, userLoginNo);

        int deleteCount = itemWishMapper.deleteWish(itemNo, userLoginNo);

        if (deleteCount == 0) {
            log.info("Delete Wish Not found - itemNo : {}, userLoginNo : {}", itemNo, userLoginNo);
            throw new StorixException(ErrorCode.WISH_NOT_FOUND);
        }

        log.info("Delete Wish Completed - itemNo : {}, userLoginNo : {}", itemNo, userLoginNo);
    }

    @Override
    public void addWishList(ItemWishDto.ItemWishRequest request, String userLoginNo) {
        log.info("Add Wish started - itemNo : {}, userLoginNo : {}", request.itemNo(), userLoginNo);

        if (itemWishMapper.existsWish(request.itemNo(), userLoginNo)) {
            throw new StorixException(ErrorCode.WISH_ALREADY_EXISTS);
        }

        int insertCount = itemWishMapper.addWish(request.itemNo(), userLoginNo, request.createdDt());

        if (insertCount == 0) {
            log.error("Insert Fail - itemNo: {}", request.itemNo());
            throw new StorixException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("Insert Wish Completed - itemNo : {}", request.itemNo());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemWishDto.ItemWishResponse> findWishList(String userLoginNo) {
        log.info("Find Wish started - userLoginNo : {}", userLoginNo);
        List<ItemWishDto.ItemWishResponse> findWish = itemWishMapper.findWish(userLoginNo);

        log.info("Find wishList completed - wishCount : {}", findWish.size());
        return findWish;
    }
}
