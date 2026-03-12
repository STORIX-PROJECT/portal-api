package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.OptionValidRequest;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.option.ItemOptionDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemMapper itemMapper;

    @Override
    public List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request) {
        String searchWord = request.searchWord();

        if(searchWord == null || searchWord.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }
        if (searchWord.trim().length() < 2) {
            throw new IllegalArgumentException("2글자 이상 입력해주세요.");
        }

        List<ItemSearchDto.ItemSearchResponse> itemList = itemMapper.searchItem(request);
        if (itemList == null || itemList.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }
       return itemList;
    }


    @Override
    public List<ItemSearchDto.ItemCategoryResponse> categorySearch(CategorySearchRequest request) {
        List<ItemSearchDto.ItemCategoryResponse> categoryList = itemMapper.searchCategory(request);
        if(categoryList == null || categoryList.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }
        return categoryList;
    }


//    @Override
//    public ItemDetailDto.ItemDetailWithImgResponse detailItem(Long itemNo) {
//        ItemDetailDto.ItemDetailResponse detail = itemMapper.detailItem(itemNo);
//
//        if(detail == null) {
//            throw new IllegalArgumentException("존재하지 않습니다.");
//        }
//        List<String> imgUrls = itemMapper.imgUrls(itemNo);
//
//        return new ItemDetailDto.ItemDetailWithImgResponse(
//                detail.itemNo(),
//                detail.itemName(),
//                detail.price(),
//                imgUrls,
//                detail.mfd(),
//                detail.exp(),
//                detail.itemDescription(),
//                detail.itemWeight(),
//                detail.itemWidth(),
//                detail.itemLength(),
//                detail.itemHeight(),
//                detail.itemRemark()
//        );
//    }
//
//
//    @Override
//    public List<ItemOptionDto.ItemOptionResponse> optionItem(Long itemNo) {
//        return itemMapper.optionItem(itemNo).stream()
//                .collect(Collectors.groupingBy(
//                        flat -> new AbstractMap.SimpleEntry<>(flat.groupNo(), flat.groupName()),
//                        LinkedHashMap::new,
//                        Collectors.mapping(
//                                flat -> new ItemOptionDto.OptionDetailResponse(flat.optionNo(), flat.optionName(), flat.price()),
//                                Collectors.toList()
//                        )
//                ))
//                .entrySet().stream()
//                .map(e -> new ItemOptionDto.ItemOptionResponse(e.getKey().getKey(), e.getKey().getValue(), e.getValue()))
//                .toList();
//    }
//
//    @Override
//    public ItemOptionDto.OptionCombiResponse optionDetailItem(OptionValidRequest.OptionDetailRequest request) {
//       if (request.optionNos() == null || request.optionNos().isEmpty()) {
//           throw new IllegalArgumentException("옵션 번호를 입력해주세요.");
//       }
//
//       ItemOptionDto.OptionCombiResponse response = itemMapper.findCombin(
//               request.itemNo(),
//               request.optionNos(),
//               request.optionNos().size()
//       );
//
//       if (response == null) {
//           throw new IllegalArgumentException("존재하지 않습니다.");
//       }
//
//       if (response.stock() <= 0) {
//           throw new IllegalArgumentException("재고가 존재하지 않습니다.");
//       }
//
//       return response;
//    }
//

}


