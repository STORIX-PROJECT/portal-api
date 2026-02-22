package com.shop.storix.portalapi.mapper.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface LoginMapper {
    void insertUserLogin (Login login);
    void insertUserOauth (OAuthLogin oAuthLogin);
    void insertUserRole (UserRole userRole);
    Optional<Login> findLoginByOAuthInfo(
            @Param("oauthProviderId") String userIdentifier,
            @Param("oauthProvider") String providerInfo
    );
    Optional<OAuthLogin> findOAuthLoginByOAuthInfo(
            @Param("oauthProviderId") String userIdentifier,
            @Param("oauthProvider") String providerInfo
    );
    List<Role>findUserRoleByLoginNo (Login login);
    int countDuplicateUser (@Param("id") String loginId, String phoneNumber);
    void insertPurchaserProfile (PurchaserProfile purchaserProfile);
    Optional<Login> findLoginByLoginId (String id);
    Optional<Login> findLoginByUserLoginNo (String userLoginNo);
    List<Role> findUserRoleByLoginId (Login login);
}
