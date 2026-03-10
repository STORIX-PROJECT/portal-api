package com.shop.storix.portalapi.service.item.facade;

import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.item.request.CategorySearchRequest;
import com.shop.storix.portalapi.model.dto.item.request.ItemSearchRequest;

import com.shop.storix.portalapi.model.dto.item.response.detail.ItemDetailDto;

import com.shop.storix.portalapi.model.dto.item.response.option.ItemDto;

import com.shop.storix.portalapi.model.dto.item.response.search.ItemSearchDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemService {
    private final com.shop.storix.portalapi.service.item.ItemService itemService;


    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchItem(ItemSearchRequest request) {
        String searchWord = request.searchWord();

        if (searchWord == null || searchWord.trim().isEmpty()) {
            return ApiResponse.fail("400","검색어를 입력해주세요.",null);}
        if(searchWord.trim().length() < 2) {
            return ApiResponse.fail("400","2글자 이상 입력해주세요.",null);}

        List<ItemSearchDto.ItemSearchResponse> list = itemService.searchItem(request);
        if (list == null || list.isEmpty()) {
            return ApiResponse.fail("404","검색 결과가 없습니다.",null);}
        return ApiResponse.ok(itemService.searchItem(request));
    }


    public ApiResponse<List<ItemSearchDto.ItemSearchResponse>> searchCategory(CategorySearchRequest request) {
        List<ItemSearchDto.ItemSearchResponse> list = itemService.categorySearch(request);
        if (list == null || list.isEmpty()) {
            return ApiResponse.fail("404","검색 결과가 없습니다.",null);
        }
        return ApiResponse.ok(itemService.categorySearch(request));
    }



}
