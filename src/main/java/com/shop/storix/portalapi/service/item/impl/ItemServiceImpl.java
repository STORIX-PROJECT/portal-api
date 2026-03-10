package com.shop.storix.portalapi.service.item.impl;

import com.shop.storix.portalapi.mapper.item.ItemMapper;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;
import com.shop.storix.portalapi.model.dto.item.response.option.ItemDto;

import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;
import com.shop.storix.portalapi.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;

    @Override
    public List<ItemSearchDto.ItemSearchResponse> searchItem(ItemSearchRequest request) {
       return itemMapper.searchItem(request);
    }

    @Override
    public List<ItemSearchDto.ItemSearchResponse> categorySearch(CategorySearchRequest request) {
        return itemMapper.searchCategory(request);
    }

}
