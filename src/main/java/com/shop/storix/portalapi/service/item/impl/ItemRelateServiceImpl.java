package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import com.shop.storix.portalapi.service.item.ItemRelateService;
import com.shop.storix.portalapi.service.item.assembler.ItemRelateAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRelateServiceImpl implements ItemRelateService {
    private final ItemMapper itemMapper;

    @Override
    public List<RelateItemDto.RelateItemResponse> relateItem(Long itemNo) {
        log.info("RelateItem search start - itemNo : {}",itemNo);

        if(itemNo == null || itemNo <= 0) {
            log.error("RelateItem search failed - Invalid itemNo : {}", itemNo);
            throw new IllegalArgumentException("유효한 상품이 아닙니다.");
        }

        List<RelateItemDto.RelateDto> relateList = itemMapper.relateItem(itemNo);

        if(CollectionUtils.isEmpty(relateList)) {
            log.warn("RelateItem not found - itemNo : {}",itemNo);
            throw new IllegalArgumentException("연관상품이 존재하지 않습니다.");
        }

        List<RelateItemDto.RelateItemResponse> response = ItemRelateAssembler.toRelateGroup(relateList);

        log.info("Relate Item search complete - resultCount : {}",response.size());
        return response;
    }
}
