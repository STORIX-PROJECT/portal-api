package com.shop.storix.portalapi.model.dto.item.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.List;

public class ItemDetailDto {

    // 내부용
    @Alias("ItemDetailResponse")
    public record ItemDetailResponse (
            Long itemNo,
            String itemName,
            Integer price,
            String mfd,
            String exp,
            String itemDescription,
            BigDecimal itemWeight,
            BigDecimal itemWidth,
            BigDecimal itemLength,
            BigDecimal itemHeight,
            String itemRemark,
            String deletedDt,
            String itemStatus
    ) {}

        // 외부 DTO
    public record ItemDetailWithImgResponse(
            @Schema(description = "상품 고유 번호",example = "1")
            Long itemNo,

            @Schema(description = "상품명",example = "나이키 에어맥스")
            String itemName,

            @Schema(description = "상품 가격",example = "150000")
            Integer price,

            @Schema(description = "상품 이미지 주소모음",example = "['https://img.com/1.jpg', 'https://img.com/2.jpg']")
            List<String> imgUrls,

            @Schema(description = "제조일자 (YYYYMMDD)",example = "20260101")
            String mfd,

            @Schema(description = "유통기한 (YYYYMMDD)",example = "20270101")
            String exp,

            @Schema(description = "상품 설명",example = "나이키 신발 입니다.")
            String itemDescription,

            @Schema(description = "상품 무게",example = "10.12")
            BigDecimal itemWeight,

            @Schema(description = "상품 가로 길이",example = "120.94")
            BigDecimal itemWidth,

            @Schema(description = "상품 세로 길이",example = "200.12")
            BigDecimal itemLength,

            @Schema(description = "상품 높이",example = "15.00")
            BigDecimal itemHeight,

            @Schema(description = "상품 비고",example = "파손주의")
            String itemRemark

    ) {}
}

