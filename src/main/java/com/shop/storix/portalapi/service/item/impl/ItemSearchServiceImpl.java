package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public List<ItemSearchDto.ItemCategoryResponse> categorySearch(CategorySearchRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.categoryNos())) {
            log.warn("Category search validation failed - categoryNos is Missing");
            throw new IllegalArgumentException("카테고리 번호를 입력해주세요.");
        }
        log.info("Category search started - CategoryNos : {}",request.categoryNos());
        try {
            List<ItemSearchDto.ItemCategoryResponse> categoryItem = itemMapper.searchCategory(request);
            if (CollectionUtils.isEmpty(categoryItem)) {
                log.warn("Category search no result - CategoryNos : {}",request.categoryNos());
                throw new IllegalArgumentException("검색 결과가 없습니다.");
            }
            log.info("Category search completed - resultCount {}",categoryItem.size());
            return categoryItem;

        } catch (IllegalArgumentException e) {
            log.error("Category search failed : {}",e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Category search error",e);
            throw e;
        }
    }

}


