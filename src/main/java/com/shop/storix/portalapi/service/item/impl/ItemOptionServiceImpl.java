package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemOptionMapper;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import com.shop.storix.portalapi.service.item.ItemOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemOptionServiceImpl implements ItemOptionService {
    private final ItemOptionMapper itemOptionMapper;

    @Override
    public ItemOptionDto.OptionValidResponse validateCombination(ItemOptionDto.ItemOptionRequest request) {

        if (request.itemNo() == null || CollectionUtils.isEmpty(request.optionNos())) {
            log.error("request blank - itemNo : {}, optionNos : {}",request.itemNo(), request.optionNos());
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        // 유효 옵션 조회
        // O(1) 조회로 빠름
        Set<Long> validOptions = new HashSet<>(itemOptionMapper.getValidOptions(request.itemNo()));

        if (validOptions.isEmpty() || !validOptions.containsAll(request.optionNos())) {
            throw new IllegalArgumentException("없는 옵션 입니다.");
        }

        // 옵션 상세정보 조회 및 변환
        List<ItemOptionDto.OptionDetail> options = itemOptionMapper.getOptionDetails(request.optionNos());

        return new ItemOptionDto.OptionValidResponse(options);
    }
}