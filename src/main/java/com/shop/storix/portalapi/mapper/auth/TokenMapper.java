package com.shop.storix.portalapi.mapper.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.Token;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TokenMapper {
    void save(Token token);
    Optional<Token> findById (String userLoginNo);
}
