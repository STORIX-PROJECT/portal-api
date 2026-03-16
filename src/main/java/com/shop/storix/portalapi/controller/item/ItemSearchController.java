package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchResponseDto;
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
    public ApiResponse<List<ItemSearchResponseDto.ItemSearchResponse>> searchItem(@ModelAttribute ItemSearchRequestDto.ItemSearchRequest request) {
        log.info("Item search request - searchWord : {}",request.searchWord());
        List<ItemSearchResponseDto.ItemSearchResponse> searchList = itemSearchService.searchItem(request);

        log.info("Item search response ready - resultCount : {}",searchList.size());
        return ApiResponse.ok(searchList);
    }


}
