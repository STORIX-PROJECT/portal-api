package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemDto;
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
    public List<ItemDto.ItemSearchResponse> searchItem(ItemSearchDto.ItemSearchRequest request) {
        String searchWord = request.searchWord();
        log.info("Item search start - searchWord : {}", searchWord);
        try {
            if (!StringUtils.hasText(searchWord) || searchWord.trim().length() < 2) {
                log.warn("Item search validation failed - searchWord: {}", searchWord);
                throw new IllegalArgumentException("검색어를 2글자 이상 입력해주세요.");
            }

            List<ItemDto.ItemSearchResponse> itemList = itemMapper.searchItem(request);
            if (CollectionUtils.isEmpty(itemList)) {
                log.warn("Item search no result - searchWord : {}",searchWord);
                throw new IllegalArgumentException("검색 결과가 없습니다.");
            }

            log.info("Item search completed - resultCount : {}",itemList.size());
            return itemList;
        } catch (IllegalArgumentException e) {
            log.warn("Item search failed - {}",e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("Item search error",e);
            throw e;
        }
    }

}


