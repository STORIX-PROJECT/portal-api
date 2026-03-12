package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.OptionValidRequest;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.option.ItemOptionDto;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemSearchService;
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
    private final ItemSearchService itemService;

    @Operation(
            summary = "상품 검색",
            description = "상품 검색을 통해 목록을 조회합니다."
    )
    @GetMapping("/search")
    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchItem(@ModelAttribute ItemSearchRequest request) {
        return ApiResponse.ok(itemService.searchItem(request));
    }


    @Operation(
            summary = "카테고리별 상품 조회",
            description = "카테고리 번호를 통해 해당 카테고리에 속한 상품 목록을 조회합니다."
    )
    @GetMapping("/category")
    public ApiResponse<List<ItemSearchDto.ItemCategoryResponse>> searchCategory(@ModelAttribute CategorySearchRequest request) {
        return ApiResponse.ok(itemService.categorySearch(request));
    }


//    @Operation(
//            summary = "상품 상세정보 조회",
//            description = "상품 번호를 통해 해당 상품의 상세 정보를 조회합니다."
//    )
//    @GetMapping("/search/detail/{itemNo}")
//    public ApiResponse<ItemDetailDto.ItemDetailWithImgResponse> itemDetail(@PathVariable Long itemNo) {
//        return ApiResponse.ok(itemService.detailItem(itemNo));
//    }
//
//    @Operation(
//            summary = "상품 옵션 정보 조회",
//            description = "상품 번호를 통해 해당 상품의 옵션 정보를 조회합니다"
//    )
//    @GetMapping("/search/option/{itemNo}")
//    public ApiResponse<List<ItemOptionDto.ItemOptionResponse>> optionItem(@PathVariable Long itemNo) {
//        return ApiResponse.ok(itemService.optionItem(itemNo));
//    }
//
//
//    @Operation(
//            summary = "옵션별 조합 조회",
//            description = "상품별 옵션 조합 및 재고 조회"
//    )
//    @PostMapping("/option/valid")
//    public ApiResponse<ItemOptionDto.OptionCombiResponse> combinOption(@RequestBody OptionValidRequest.OptionDetailRequest request) {
//        return ApiResponse.ok(itemService.optionDetailItem(request));
//    }

}
