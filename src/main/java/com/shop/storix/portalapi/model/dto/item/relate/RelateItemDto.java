package com.shop.storix.portalapi.model.dto.item.relate;
import lombok.Builder;
import org.apache.ibatis.type.Alias;

public class RelateItemDto {

    // 내부용 DTO
    @Alias("RelateDto")
    public record RelateDto (
            Long itemNo,
            String itemName,
            Integer price,
            String itemStatus,
            String imgUrl,
            Integer orderCount
    ) {}

    // Controller Reponse 용답용
    @Builder
    public record RelateItemResponse (
            Long itemNo,
            String itemName,
            String itemStatus,
            Integer price,
            String imgUrl,
            Integer orderCount
    ) {}
}
