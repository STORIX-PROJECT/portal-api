package com.shop.storix.portalapi.mapper.item;
import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import com.shop.storix.portalapi.model.dto.item.search.ItemSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface ItemMapper {
    List<RelateItemDto.RelateDto> relateItem(Long itemNo);
    List<ItemSearchDto.ItemSearchResponse> searchItem(
            @Param("request") ItemSearchDto.ItemSearchRequest request,
            @Param("newDate") String newDate);
}
