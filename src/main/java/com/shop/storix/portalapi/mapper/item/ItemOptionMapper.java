package com.shop.storix.portalapi.mapper.item;

import com.shop.storix.portalapi.model.dto.item.option.ItemOptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemOptionMapper {
    // 선택한 옵션들의 정보 조회
    List<ItemOptionDto.OptionDetail> getOptionDetails(@Param("optionNos") List<Long> optionNos);

    int countOptionGroups(@Param("itemNo") Long itemNo);
}
