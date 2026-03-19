package com.shop.storix.portalapi.controller.item;


import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "ItemSearch", description = "상품 검색 및 목록 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

    @Operation(
            summary = "상품 검색",
            description = "검색어를 통해 상품을 조회합니다."
    )
    @GetMapping("/search")
    public ApiResponse<List<ItemSearchDto.ControllerResponse>> searchItem(@RequestBody ItemSearchDto.ItemSearchRequest request) {

        // 서버 내부 DTO 조회
        List<ItemSearchDto.ItemSearchResponse> itemSearchList = itemSearchService.searchItem(request);

        // Controller Response 변환
        List<ItemSearchDto.ControllerResponse> response = itemSearchList.stream()
                .map(item -> ItemSearchDto.ControllerResponse.builder()
                        .itemNo(item.itemNo())
                        .itemName(item.itemName())
                        .price(item.price())
                        .itemStatus(item.itemStatus())
                        .imgUrl(item.imgUrl())
                        .isNew(item.isNew())
                        .orderCount(item.orderCount())
                        .reviewCount(item.reviewCount())
                        .build())
                .toList();
        return ApiResponse.ok(response);
    }


}
