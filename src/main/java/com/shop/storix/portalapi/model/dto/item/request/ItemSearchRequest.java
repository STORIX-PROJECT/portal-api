package com.shop.storix.portalapi.model.dto.item.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ItemSearchRequest(
        @Schema(description = "검색어", example = "아디다스")
        String searchWord,

        @Schema(description = "카테고리 번호",example = "[10,11]")
        List<Long> categoryNos,

        @Schema(description = "정렬 타입 최신순:NEW, 낮은가격순:PRICE_ASC, 높은가격순:PRICE_DESC",example = "PRICE_ASC")
        String sortType, // NEW, PRICE_ASC, PRICE_DESC

        @Schema(description = "조회할 페이지",example = "1")
        Integer page,

        @Schema(description = "한 페이지당 노출 상품수",example = "10")
        Integer size
) {}