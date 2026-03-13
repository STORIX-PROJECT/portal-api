package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailOptionDto;
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
            summary = "상품 상세정보 조회",
            description = "상품 번호를 통해 해당 상품의 상세 정보를 조회합니다."
    )
    @GetMapping("/search/detail/{itemNo}")
    public ApiResponse<ItemDetailDto.ItemDetailWithImgResponse> itemDetail(@PathVariable Long itemNo) {
        log.info("ItemDetail search request - itemNo : {}",itemNo);
        ItemDetailDto.ItemDetailWithImgResponse detail = itemSearchService.detailItem(itemNo);

        log.info("ItemDetail search response ready - itemNo : {}",itemNo);
        return ApiResponse.ok(detail);
    }

    @Operation(
            summary = "상품 옵션정보 조회",
            description = "상품 번호를 통해 해당 상품의 옵션 정보를 조회합니다."
    )
    @GetMapping("/search/detail/{itemNo}/option")
    public ApiResponse<List<ItemDetailOptionDto.OptionGroupResponse>> detailOption(@PathVariable Long itemNo) {
        log.info("Item Option serach request - itemNo : {}",itemNo);
        List<ItemDetailOptionDto.OptionGroupResponse> result = itemSearchService.detailOption(itemNo);

        log.info("Item Option search response ready - itemNo : {}, groupCount : {}",itemNo,result.size());
        return ApiResponse.ok(result);
    }
}