package com.shop.storix.portalapi.controller.item;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.model.dto.item.wish.ItemWishDto;
import com.shop.storix.portalapi.service.item.ItemWishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            summary = "상품 위시리스트 삭제",
            description = "상품번호를 통해 로그인한 사용자의 위시를 삭제합니다."
    )
    @DeleteMapping("/{itemNo}")
    public ApiResponse<String> deleteWish(@PathVariable Long itemNo,
                                          @AuthenticationPrincipal UserPrincipal principal) {
        log.info("Delete Wish request - itemNo : {}, userLoginNo : {}", itemNo, principal.getUsername());
        itemWishService.deleteWish(itemNo, principal.getUsername());
        return ApiResponse.ok("위시 해제 완료");
    }

    @Operation(
            summary = "상품 위시리스트 추가",
            description = "상품번호를 통해 로그인한 사용자의 위시리스트에 추가합니다."
    )
    @PostMapping("/add")
    public ApiResponse<String> addWish(@Valid @RequestBody ItemWishDto.ItemWishRequest request,
                                       @AuthenticationPrincipal UserPrincipal principal) {
        log.info("ItemWish add request - itemNo : {}, userLoginNo : {}", request.itemNo(), principal.getUsername());
        itemWishService.addWishList(request, principal.getUsername());
        return ApiResponse.ok("위시리스트 추가 성공");
    }

    @Operation(
            summary = "상품 위시리스트 조회",
            description = "로그인한 사용자의 위시리스트를 조회합니다."
    )
    @GetMapping("/find")
    public ApiResponse<List<ItemWishDto.ItemWishResponse>> findWish(
            @AuthenticationPrincipal UserPrincipal principal) {
        log.info("Find ItemWish request - userLoginNo : {}", principal.getUsername());
        List<ItemWishDto.ItemWishResponse> wishList = itemWishService.findWishList(principal.getUsername());
        log.info("Find WishList response ready - wishCount : {}", wishList.size());
        return ApiResponse.ok(wishList);
    }
}
