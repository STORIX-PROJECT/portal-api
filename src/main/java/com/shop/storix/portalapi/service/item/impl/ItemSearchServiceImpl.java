package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.common.ErrorCode;
import com.shop.storix.portalapi.common.exception.StorixException;
import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import com.shop.storix.portalapi.service.item.assembler.ItemSearchAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public List<ItemSearchDto.ControllerResponse> searchItem(ItemSearchDto.ItemSearchRequest request) {
        String searchWord = request.searchWord();
        log.info("Item search start - searchWord : {}", searchWord);

        if (searchWord == null || searchWord.trim().length() < 2) {
            log.error("Item search validation failed - searchWord: {}", searchWord);
            throw new StorixException(ErrorCode.INVALID_INPUT);
        }

        List<ItemSearchDto.ItemSearchResponse> itemList = itemMapper.searchItem(request);
        if (CollectionUtils.isEmpty(itemList)) {
            log.warn("Item Search not found - searchWord : {}",request.searchWord());
            return Collections.emptyList();
        }

        List<ItemSearchDto.ControllerResponse> response = ItemSearchAssembler.toSearchGroup(itemList);
        log.info("Item search completed - resultCount : {}",itemList.size());
        return response;
    }
}
