package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.AuthUser;
import com.shop.storix.portalapi.model.dto.auth.domain.LoginDto;
import com.shop.storix.portalapi.model.dto.auth.domain.Role;
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
    public AuthUser getUserAuth(String userLoginNo) {
        LoginDto login = loginMapper.findLoginByUserLoginNo(userLoginNo).orElseThrow(); // HACK: 에러 처리 해야함
        List<Role> roles = loginMapper.findUserRoleByLoginNo(login);
        return new AuthUser(login,roles);
    }
}
