package com.shop.storix.portalapi.model.dto.item.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategorySearchRequest (
        @Schema(description = "카테고리 번호",example = "10")
        List<Long> categoryNos,
        @Schema(description = "정렬 타입 최신순:NEW, 신상품:BEST, 가격순:PRICE",example = "PRICE")
        String sortType,
        @Schema(description = "조회할 페이지",example = "1")
        Integer page,
        @Schema(description = "한 페이지당 노출 상품수",example = "10")
        Integer size
) {}

