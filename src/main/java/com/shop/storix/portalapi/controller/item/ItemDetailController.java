package com.shop.storix.portalapi.controller.item;


import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import com.shop.storix.portalapi.service.item.ItemDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "ItemDetail", description = "상품 상세정보 및 옵션 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemDetailController {
    private final ItemDetailService itemDetailService;

    @Operation(
            summary = "상품 상세정보 조회",
            description = "상품 번호를 통해 해당 상품의 상세 정보를 조회합니다."
    )
    @GetMapping("/{itemNo}")
    public ApiResponse<ItemDetailDto.ItemDetailWithImgResponse> itemDetail(@PathVariable Long itemNo) {
        log.info("ItemDetail search request - itemNo : {}",itemNo);
        ItemDetailDto.ItemDetailWithImgResponse response = itemDetailService.detailItem(itemNo);

        log.info("ItemDetail search response ready - itemNo : {}",itemNo);
        return ApiResponse.ok(response);
    }

    @Operation(
            summary = "상품 옵션정보 조회",
            description = "상품 번호를 통해 해당 상품의 옵션 정보를 조회합니다."
    )
    @GetMapping("/{itemNo}/options")
    public ApiResponse<List<ItemOptionDto.OptionGroupResponse>> detailOption(@PathVariable Long itemNo) {
        log.info("Item Option serach request - itemNo : {}",itemNo);
        List<ItemOptionDto.OptionGroupResponse> result = itemDetailService.detailOption(itemNo);

        log.info("Item Option search response ready - itemNo : {}, groupCount : {}",itemNo,result.size());
        return ApiResponse.ok(result);
    }
}
