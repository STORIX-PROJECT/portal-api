package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.ItemWishRequestDto;
import com.shop.storix.portalapi.model.dto.item.response.wish.ItemWishResponseDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "ItemWish", description = "상품 위시리스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wish")
public class ItemWishController {
    private final ItemWishService itemWishService;

    @Operation(
            summary = "상품 위시리스트 추가",
            description = "사용자번호, 상품번호를 통해 상품의 위시리스트 추가합니다."
    )
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addWish(@RequestBody ItemWishRequestDto.WishRequest request) {
        log.info("ItemWish add request - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());
        itemWishService.addWishList(request);
        return ResponseEntity.ok(ApiResponse.ok("위시 추가완료"));
    }

    @Operation(
            summary = "상품 위시리스트 조회",
            description = "내가 추가한 위시리스트 조회"
    )
    @GetMapping("/find/{userLoginNo}")
    public ResponseEntity<ApiResponse<List<ItemWishResponseDto.ItemWishResponse>>> findWish(@PathVariable String userLoginNo) {
        log.info("Find ItemWish request - userLoginNo : {}",userLoginNo);
        List<ItemWishResponseDto.ItemWishResponse> wishList = itemWishService.findWishList(userLoginNo);

        log.info("Find WishList response ready - wishCount : {}",wishList.size());
        return ResponseEntity.ok(ApiResponse.ok(wishList));
    }
}
