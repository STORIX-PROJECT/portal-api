package com.shop.storix.portalapi.mapper.auth;

import com.shop.storix.portalapi.model.dto.auth.AccountStatus;
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
    Optional<AuthDto.Login> findLoginByLoginId (String id);
    Optional<AuthDto.Login> findLoginByUserLoginNo (String userLoginNo);
    List<AuthDto.Role> findUserRoleByLoginId (AuthDto.Login login);
    void increaseFailCount(@Param("id") String loginId);
    void resetFailCount(@Param("id") String loginId);
    void updateUserLoginStatus(@Param("id") String id,
                       @Param("status") AccountStatus status);
    Optional<String> findLoginIdByEmail(@Param("email") String email);
    int existsProfileByUserLoginNo(String userLoginNo);
    int existsOauthByEmail(String email);
}

