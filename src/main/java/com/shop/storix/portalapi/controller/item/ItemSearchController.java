package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.search.CateogrySearchDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
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
            summary = "카테고리별 상품 조회",
            description = "카테고리 번호를 통해 해당 카테고리에 속한 상품 목록을 조회합니다."
    )
    @GetMapping("/category")
    public ApiResponse<List<ItemSearchDto.ItemCategoryResponse>> searchCategory(@RequestBody CateogrySearchDto.CategorySearchRequest request) {
        log.info("Category search request - CategoryNos : {}",request.categoryNos());
        List<ItemSearchDto.ItemCategoryResponse> searchCategory = itemSearchService.categorySearch(request);

        log.info("Category search response ready - resultCount : {}",searchCategory.size());
        return ApiResponse.ok(searchCategory);
    }
}
