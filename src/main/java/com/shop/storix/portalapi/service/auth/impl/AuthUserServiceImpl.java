package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.service.auth.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final LoginMapper loginMapper;

    @Override
    public AuthDto.AuthUser getUserAuth(String userLoginNo) {
        AuthDto.Login login = loginMapper.findLoginByUserLoginNo(userLoginNo).orElseThrow(); // HACK: 에러 처리 해야함
        List<AuthDto.Role> roles = loginMapper.findUserRoleByLoginNo(login);
        return new AuthDto.AuthUser(login,roles);
    }
}
