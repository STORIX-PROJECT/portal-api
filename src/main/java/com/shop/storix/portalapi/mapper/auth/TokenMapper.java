package com.shop.storix.portalapi.mapper.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TokenMapper {
    void save(AuthDto.Token token);
    Optional<AuthDto.Token> findById (String userLoginNo);
}
