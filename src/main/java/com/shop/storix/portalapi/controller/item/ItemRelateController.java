package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import com.shop.storix.portalapi.service.item.ItemRelateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "ItemRelate", description = "연관 상품 검색 및 목록 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/relate")
public class ItemRelateController {
    private final ItemRelateService itemRelateService;

    @Operation(
            summary = "연관상품 검색",
            description = "연관상품 검색합니다"
    )
    @GetMapping("/search/{itemNo}")
    public ResponseEntity<ApiResponse<List<RelateItemDto.RelateItemResponse>>> relateItem(@PathVariable Long itemNo) {
        log.info("RelateItem search request - itemNo : {}",itemNo);
        List<RelateItemDto.RelateItemResponse> response = itemRelateService.relateItem(itemNo);

        log.info("RelateItem search response - resultCount : {}",response.size());

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
