package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;

import com.shop.storix.portalapi.model.dto.item.response.option.ItemDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.facade.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item", description = "상품 검색 및 목록 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {
    private final ItemService itemService;

    @Operation(
            summary = "상품 검색",
            description = "상품 검색을 통해 목록을 조회합니다."
    )
    @GetMapping("/search")
    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchItem(@ModelAttribute ItemSearchRequest request) {
        return itemService.searchItem(request);
    }


    @Operation(
            summary = "카테고리별 상품 조회",
            description = "카테고리 번호를 통해 해당 카테고리에 속한 상품 목록을 조회합니다."
    )
    @GetMapping("/category")
    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchCategory(@ModelAttribute CategorySearchRequest request) {
        return itemService.searchCategory(request);
    }


}
