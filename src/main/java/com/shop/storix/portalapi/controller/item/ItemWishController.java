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
            description = "사용자번호, 상품번호를 통해 해당 상품의 위시를 삭제합니다."
    )
    @DeleteMapping("/delete")
    public ApiResponse<String> deleteWish(@Valid @RequestBody ItemWishDto.DeleteWishRequest request) {
        log.info("Delete Wish request - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());
        itemWishService.deleteWish(request);
        return ApiResponse.ok("위시 해제 완료");
            summary = "상품 위시리스트 추가",
            description = "사용자번호, 상품번호를 통해 상품의 위시리스트 추가합니다."
    )
    @PostMapping("/add")
    public ApiResponse<?> addWish(@Valid @RequestBody ItemWishDto.ItemWishRequest request) {
        log.info("ItemWish add request - itemNo : {}, userLoginNo : {}",request.itemNo(), request.userLoginNo());

        itemWishService.addWishList(request);
        return ApiResponse.ok(request);
    }


    @Operation(
            summary = "상품 위시리스트 조회",
            description = "내가 추가한 위시리스트 조회"
    )
    @GetMapping("/find/{userLoginNo}")
    public ApiResponse<List<ItemWishDto.ItemWishResponse>> findWish(
            @PathVariable String userLoginNo
    ) {
        log.info("Find ItemWish request - userLoginNo : {}",userLoginNo);
        List<ItemWishDto.ItemWishResponse> wishList = itemWishService.findWishList(userLoginNo);

        log.info("Find WishList response ready - wishCount : {}",wishList.size());
        return ApiResponse.ok(wishList);
    }
}
