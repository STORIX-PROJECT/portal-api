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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        if (searchWord.trim().length() < 2) {
            log.warn("Item search validation failed - searchWord: {}", searchWord);
            throw new StorixException(ErrorCode.INVALID_INPUT);
        }

        // 신상품 기준일 계산
        int days = (request.newStandard() == null) ? 15 : request.newStandard();
        String newDate = LocalDate.now().minusDays(days).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("newDate : {}",newDate);

        List<ItemSearchDto.ItemSearchResponse> itemList = itemMapper.searchItem(request,newDate);

        if (CollectionUtils.isEmpty(itemList)) {
            log.warn("Item Search not found - searchWord : {}",request.searchWord());
            return Collections.emptyList();
        }

        List<ItemSearchDto.ControllerResponse> response = ItemSearchAssembler.toSearchGroup(itemList);
        log.info("Item search completed - resultCount : {}",itemList.size());
        return response;
    }
}
