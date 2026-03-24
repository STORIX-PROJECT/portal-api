package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;
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
    public List<ItemSearchDto.ItemCategoryResponse> categorySearch(ItemSearchDto.CategorySearchRequest request) {
        log.info("Category search started - CategoryNos : {}",request != null ? request.categoryNos() : null);

        List<ItemSearchDto.ItemCategoryResponse> categoryItem = itemMapper.searchCategory(request);

        if (categoryItem.isEmpty()) {
            log.warn("No categories found - CategoryNos : {}", request.categoryNos());
        }

        log.info("Category search completed - resultCount {}",categoryItem.size());
        return categoryItem;
    }
}


