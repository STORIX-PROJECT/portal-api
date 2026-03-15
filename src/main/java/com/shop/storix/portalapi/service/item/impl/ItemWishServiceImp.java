package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.ItemWishRequestDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemWishServiceImp implements ItemWishService {
    private final ItemMapper itemMapper;

    @Override
    public void deleteWish(ItemWishRequestDto.DeleteWishRequest request) {
        log.info("Delete Wish started - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());
        try {
            if (request.itemNo() == null || request.userLoginNo() == null) {
                log.warn("Delete Wish failed - request Blank");
                throw new IllegalArgumentException("공백입니다.");
            }

            if (!itemMapper.existsWish(request)) {
                log.warn("Delete Wish Not found - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());
                throw new IllegalArgumentException("위시가 존재하지 않습니다.");
            }

            itemMapper.deleteWish(request);
            log.info("Delete Wish Completed - itemNo : {}, userLoginNo : {}", request.itemNo(), request.userLoginNo());

        } catch (IllegalArgumentException e) {
            log.warn("Delete Wish failed - {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("Delete Wish error", e);
            throw e;
        }
    }

}
