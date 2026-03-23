package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemDetailMapper;
import com.shop.storix.portalapi.model.dto.item.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import com.shop.storix.portalapi.service.item.ItemDetailService;
import com.shop.storix.portalapi.service.item.assembler.ItemDetailAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemDetailServiceImpl implements ItemDetailService {
    private final ItemDetailMapper itemDetailMapper;

    @Override
    public ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo) {
        log.info("ItemDetail search started - itemNo : {}",itemNo);

        // 내부용 DTO로 받기
        ItemDetailDto.ItemDetailResponse detail = itemDetailMapper.detailItem(itemNo);

        if(detail == null || detail.deletedDt() != null || !"ACTIVE".equals(detail.itemStatus())) {
            log.warn("ItemDetail search validation failed - itemNo : {}",itemNo);
            throw new IllegalArgumentException("존재하지 않거나 판매가 중단된 상품입니다.");
        }

        List<String> imgUrls = itemDetailMapper.imgUrls(itemNo);
        log.info("ItemDetail completed - itemNo : {}",itemNo);

        // 외부용 DTO로 변환
        return ItemDetailAssembler.toDetailGroup(detail,imgUrls);
    }

    @Override
    public List<ItemOptionDto.OptionGroupResponse> detailOption(Long itemNo) {
        log.info("Item Option detail started - itemNo: {}", itemNo);

        // 내부용 DTO로 받기
        List<ItemOptionDto.ItemDetailOptionResponse> optionList = itemDetailMapper.optionDetail(itemNo);

        if (CollectionUtils.isEmpty(optionList)) {
            log.warn("Item Option detail not found - itemNo : {}", itemNo);
            throw new IllegalArgumentException("옵션이 존재하지 않습니다.");
        }

        // 조합
        List<ItemOptionDto.OptionGroupResponse> result = ItemDetailAssembler.toGroup(optionList);

        log.info("Item option detail completed - itemNo: {}, groupCount: {}", itemNo, result.size());
        return result;
    }
}
