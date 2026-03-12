package com.shop.storix.portalapi.mapper.item;


import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;
import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request);
    List<ItemSearchDto.ItemCategoryResponse> searchCategory(CategorySearchRequest request);
//    ItemDetailDto.ItemDetailResponse detailItem(Long itemNo);
//    List<String> imgUrls(Long itemNo);
//    List<ItemOptionDto.ItemOptionDetailResponse> optionItem(Long itemNo);
//    ItemOptionDto.OptionCombiResponse findCombin(@Param("itemNo") Long itemNo, @Param("optionNos") List<Long> optionNos, @Param("optionCount") int optionCount);
}
