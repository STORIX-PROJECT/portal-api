package com.shop.storix.portalapi.service.item.impl;
import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailOptionDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo) {
        log.info("ItemDetail search started - itemNo : {}",itemNo);

        try {
            ItemDetailDto.ItemDetailResponse detail = itemMapper.detailItem(itemNo);
            if(detail == null || detail.deletedDt() != null || !"ACTIVE".equals(detail.itemStatus())) {
                log.warn("ItemDetail search validation failed - itemNo : {}",itemNo);
                throw new IllegalArgumentException("존재하지 않거나 판매가 중단된 상품입니다.");
            }
            List<String> imgUrls = itemMapper.imgUrls(itemNo);
            log.info("ItemDetail completed - itemNo : {}",itemNo);

            return new ItemDetailDto.ItemDetailWithImgResponse(
                    detail.itemNo(),
                    detail.itemName(),
                    detail.price(),
                    imgUrls,
                    detail.mfd(),
                    detail.exp(),
                    detail.itemDescription(),
                    detail.itemWeight(),
                    detail.itemWidth(),
                    detail.itemLength(),
                    detail.itemHeight(),
                    detail.itemRemark()
            );
        } catch (IllegalArgumentException e) {
            log.warn("ItemDetail failed - {}",e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("ItemDetail error",e);
            throw e;
        }

    }

//    @Override
//    public List<ItemDetailOptionDto.OptionGroupResponse> detailOption(Long itemNo) {
//        return itemMapper.optionDetail(itemNo).stream()  // ItemDetailOptionResponse 리스트 반환
//                .collect(Collectors.groupingBy(
//                        flat -> new AbstractMap.SimpleEntry<>(flat.groupNo(), flat.groupName()),
//                        LinkedHashMap::new,
//                        Collectors.mapping(
//                                flat -> new ItemDetailOptionDto.OptionResponse(flat.optionNo(), flat.optionName(), flat.price()),
//                                Collectors.toList()
//                        )
//                ))
//                .entrySet().stream()
//                .map(e -> new ItemDetailOptionDto.OptionGroupResponse(e.getKey().getKey(), e.getKey().getValue(), e.getValue()))
//                .toList();
//    }
//

}


