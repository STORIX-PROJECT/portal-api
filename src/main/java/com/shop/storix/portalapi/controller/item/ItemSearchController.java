package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.response.relate.RelateItemDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
            summary = "연관상품 검색",
            description = "연관상품 검색합니다"
    )
    @GetMapping("/search/relate/{itemNo}")
    public ResponseEntity<ApiResponse<List<RelateItemDto.RelateItemResponse>>> relateItem(@PathVariable Long itemNo) {
        log.info("Relate Item search request - itemNo : {}",itemNo);
        List<RelateItemDto.RelateItemResponse> result = itemSearchService.relateItem(itemNo);

        log.info("Relate Item search response ready - resultCount : {}",result.size());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

}
