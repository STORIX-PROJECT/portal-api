package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Item", description = "상품 검색 및 목록 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {
    private final ItemSearchService itemService;

    @Operation(
            summary = "상품 검색",
            description = "상품 검색을 통해 목록을 조회합니다."
    )
    @GetMapping("/search")
    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchItem(@ModelAttribute ItemSearchRequest request) {
        log.info("상품 검색 요청 - {}",request);
        List<ItemSearchDto.ItemSearchResponse> searchList = itemService.searchItem(request);

        log.info("검색 응답 준비 완료 - {}",searchList);
        return ApiResponse.ok(searchList);
    }

}
