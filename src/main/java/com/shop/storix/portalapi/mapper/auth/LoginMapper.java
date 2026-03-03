package com.shop.storix.portalapi.mapper.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LoginMapper {
    void insertUserLogin (AuthDto.Login login);
    void insertUserOauth (AuthDto.OAuthLogin oAuthLoginDto);
    void insertUserRole (AuthDto.UserRole userRole);
    Optional<AuthDto.Login> findLoginByOAuthInfo(
            @Param("oauthProviderId") String userIdentifier,
            @Param("oauthProvider") String providerInfo
    );
    Optional<AuthDto.OAuthLogin> findOAuthLoginByOAuthInfo(
            @Param("oauthProviderId") String userIdentifier,
            @Param("oauthProvider") String providerInfo
    );
    List<AuthDto.Role>findUserRoleByLoginNo (AuthDto.Login login);
    int countDuplicateUser (@Param("id") String loginId, String phoneNumber);
    void insertPurchaserProfile (PurchaserDto.Purchaser purchaserDto);
    Optional<AuthDto.Login> findLoginByLoginId (String id);
    Optional<AuthDto.Login> findLoginByUserLoginNo (String userLoginNo);
    List<AuthDto.Role> findUserRoleByLoginId (AuthDto.Login login);
}
