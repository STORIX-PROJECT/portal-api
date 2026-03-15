package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.response.relate.RelateItemDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
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
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public List<RelateItemDto.RelateItemResponse> relateItem(Long itemNo) {
        log.info("Relate Item search start - itemNo : {}",itemNo);
        List<RelateItemDto.RelateItemResponse> relate = itemMapper.relateItem(itemNo);
        try {
            if(CollectionUtils.isEmpty(relate)) {
                log.warn("Relate Item no result - itemNo : {}",itemNo);
                throw new IllegalArgumentException("연관상품이 존재하지 않습니다.");
            }
            log.info("Relate Item search complete - resultCount : {}",relate.size());
            return relate;
        } catch (IllegalArgumentException e) {
            log.warn("Relate Item search failed - {}",e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("Relate Item search error",e);
            throw e;
        }
    }


}


