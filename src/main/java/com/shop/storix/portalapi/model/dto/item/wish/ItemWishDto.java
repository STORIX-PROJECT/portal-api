package com.shop.storix.portalapi.model.dto.item.wish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

public class ItemWishDto {
    public record DeleteWishRequest (
            @NotBlank
            String userLoginNo,
            @NotNull
            Long itemNo
    ) {}

    @Alias("ItemWishRequest")
    public record ItemWishRequest (
            @NotBlank
            @Schema(description = "사용자 고유 번호",example = "1")
            String userLoginNo,

            @NotNull
            @Schema(description = "상품 고유 번호", example = "1")
            Long itemNo,

            @Schema(description = "위시 추가 날짜", example = "20260101")
            String createdDt
    ) {}


    @Alias("ItemWishResponse")
    public record ItemWishResponse (
            String userLoginNo,
            Long itemNo,
            String itemName,
            String createdDt
    ) {}
}
