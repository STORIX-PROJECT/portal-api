package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemOptionMapper;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import com.shop.storix.portalapi.service.item.ItemOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        List<ItemOptionDto.OptionDetail> options = itemOptionMapper.getOptionDetails(request.optionNos());

        if (options.size() != request.optionNos().size()) {
            log.error("Invalid option numbers found");
            throw new IllegalArgumentException("유효하지 않은 옵션 번호가 포함되어 있습니다.");
        }

        Set<Long> groups = options.stream()
                .map(ItemOptionDto.OptionDetail::groupNo)
                .collect(Collectors.toSet());

        int totalGroups = itemOptionMapper.countOptionGroups(request.itemNo());

        if (groups.size() != options.size()) {
            log.warn("Duplicate group selection");
            throw new IllegalArgumentException("동일한 옵션 그룹의 항목을 중복해서 선택할 수 없습니다.");
        }

        if (groups.size() != totalGroups) {
            log.warn("Incomplete option selection");
            throw new IllegalArgumentException("모든 필수 옵션을 선택해야 합니다.");
        }

        return new ItemOptionDto.OptionValidResponse(options);
    }
}