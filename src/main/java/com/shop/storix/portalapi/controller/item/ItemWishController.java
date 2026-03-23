package com.shop.storix.portalapi.controller.item;


import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "ItemWish", description = "상품 위시리스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wish")
public class ItemWishController {
    private final ItemWishService itemWishService;

    @Operation(
            summary = "상품 위시리스트 삭제",
            description = "사용자번호, 상품번호를 통해 해당 상품의 위시를 삭제합니다."
    )
    @DeleteMapping("/delete")
    public ApiResponse<?> deleteWish(@Valid @RequestBody ItemWishDto.DeleteWishRequest request) {
        log.info("Delete Wish request - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());
        itemWishService.deleteWish(request);
        return ApiResponse.ok();
    }
}
