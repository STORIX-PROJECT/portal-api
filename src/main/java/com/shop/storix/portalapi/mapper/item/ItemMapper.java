package com.shop.storix.portalapi.mapper.item;
import com.shop.storix.portalapi.model.dto.item.relate.RelateItemDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface ItemMapper {
    List<RelateItemDto.RelateDto> relateItem(Long itemNo);
}
