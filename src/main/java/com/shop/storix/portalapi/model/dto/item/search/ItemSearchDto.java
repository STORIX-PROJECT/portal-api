package com.shop.storix.portalapi.model.dto.item.search;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.apache.ibatis.type.Alias;

import java.util.List;


public class ItemSearchDto {
    public record ItemSearchRequest(
            @Schema(description = "검색어", example = "아디다스")
            @NotBlank(message = "검색어를 입력해주세요")
            String searchWord,

            @Schema(description = "카테고리 번호",example = "[10,11]")
            List<Long> categoryNos,

            @Schema(description = "정렬 타입 최신순:NEW, 가격순 : PRICE, 인기순 : BEST",example = "PRICE")
            String sortType,

            @Schema(description = "조회할 페이지",example = "1")
            @Min(1)
            Integer page,

            @Schema(description = "한 페이지당 노출 상품수",example = "10")
            @Min(1)
            Integer size,

            @Schema(description = "신상품 기준 일수",example = "15")
            @Min(1)
            Integer newStandard
    ) {
        // 페이지 번호를 offSet으로 변환 (페이지 1 = offSet 0)
        public Integer offSet() {
            if (page == null || size == null) {
                return null;
            }
            return (page - 1) * size;
        }
    }


    // 실제 응답 Controller Response
    @Builder
    public record ControllerResponse (
            Long itemNo,
            String itemName,
            Integer price,
            String itemStatus,
            String imgUrl,
            boolean isNew,
            Integer orderCount,
            Integer reviewCount
    ) {}


    // 내부용 DTO
    @Alias("ItemSearchResponse")
    public record ItemSearchResponse(
            Long itemNo,
            String itemName,
            Integer price,
            String itemStatus,
            String imgUrl,
            boolean isNew,
            Integer orderCount,
            Integer reviewCount
    ) {}
}
