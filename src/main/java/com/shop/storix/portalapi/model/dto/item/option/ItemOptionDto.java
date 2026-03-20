package com.shop.storix.portalapi.model.dto.item.option;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ItemOptionDto {

    @Schema(description = "옵션 조합 검증 요청")
    public record ItemOptionRequest (
            @Schema(description = "상품 고유 ID", example = "1")
            Long itemNo,

            @Schema(description = "선택한 옵션 고유 ID 목록",example = "[1,4]")
            List<Long> optionNos
    ) {}


    // 최종 출력은 이 부분
    @Schema(description = "옵션 검증 응답")
    public record OptionValidResponse(
            @Schema(description = "옵션 상세 정보 목록")
            List<ItemOptionDto.OptionDetail> options
    ) {}


    // 옵션 정보 출력
    @Schema(description = "옵션 상세 정보")
    public record OptionDetail(
            @Schema(description = "옵션 고유 ID",example = "1")
            Long optionNo,

            @Schema(description = "옵션 그룹 고유 ID",example = "1")
            Long groupNo,

            @Schema(description = "옵션 그룹명",example = "색상")
            String groupName,

            @Schema(description = "옵션명",example = "빨강")
            String optionName

    ) {}
}
