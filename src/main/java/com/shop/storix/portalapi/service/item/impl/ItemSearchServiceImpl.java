package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request) {
        String searchWord = request.searchWord();
        log.info("검색어 검증 시작 : {}", searchWord);

        try {
            if (searchWord == null || searchWord.trim().isEmpty()) {
                log.warn("검증 실패 : 검색어 누락");
                throw new IllegalArgumentException("검색어를 입력해주세요.");
            }
            if (searchWord.trim().length() < 2) {
                log.warn("검증 실패 : 검색 글자 수 부족 ({})", searchWord);
                throw new IllegalArgumentException("2글자 이상 입력해주세요.");
            }

            List<ItemSearchDto.ItemSearchResponse> itemList = itemMapper.searchItem(request);

            if (itemList == null || itemList.isEmpty()) {
                log.warn("검색 결과 없음 : {}", searchWord);
                throw new IllegalArgumentException("검색 결과가 없습니다.");
            }

            log.info("검색 완료 : {}",itemList);
            return itemList;

        } catch (IllegalArgumentException e) {
            log.error("비즈니스 로직 예외 발생 : {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("시스템 에러 발생", e);
            throw e;
        }
    }



}


