package com.shop.storix.portalapi.model.dto.item.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ItemSearchRequestDto {
        public record ItemSearchRequest(
                @Schema(description = "검색어", example = "아디다스")
                String searchWord,

                @Schema(description = "카테고리 번호",example = "[10,11]")
                List<Long> categoryNos,

                @Schema(description = "정렬 타입 최신순:NEW, 가격순 : PRICE, 인기순 : BEST",example = "PRICE")
                String sortType,

                @Schema(description = "조회할 페이지",example = "1")
                Integer page,

                @Schema(description = "한 페이지당 노출 상품수",example = "10")
                Integer size
        ) {}
}
