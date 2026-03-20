package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.service.auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginMapper loginMapper;

    @Override
    public String findLoginIdByEmail(String email) {
        return loginMapper.findLoginIdByEmail(email).orElseThrow(); //HACK: 예외처리 구현해야함
    }
}
