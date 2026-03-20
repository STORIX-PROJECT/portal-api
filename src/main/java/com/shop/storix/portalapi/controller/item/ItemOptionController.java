package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import com.shop.storix.portalapi.service.item.ItemOptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item-option")
public class ItemOptionController {
    private final ItemOptionService itemOptionService;

    @Operation(summary = "옵션 조합 검증", description = "선택한 옵션 조합의 유효성 및 재고를 확인합니다.")

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<ItemOptionDto.OptionValidResponse>> validateCombination(@RequestBody ItemOptionDto.ItemOptionRequest request) {
        log.info("Combination validate request - itemNo: {}, optionNos: {}", request.itemNo(), request.optionNos());

        ItemOptionDto.OptionValidResponse response = itemOptionService.validateCombination(request);
        log.info("Combination validate response ready - responseSize :{}",response.options().size());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
