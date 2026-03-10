package com.shop.storix.portalapi.mapper.purchaser;

import com.shop.storix.portalapi.model.dto.purchaser.domain.PurchaserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PurchaserMapper {
    void insertPurchaserProfile (PurchaserDto.Purchaser purchaserDto);
    Optional<PurchaserDto.Purchaser> selectPurchaserProfile (String userLoginNo);
}
