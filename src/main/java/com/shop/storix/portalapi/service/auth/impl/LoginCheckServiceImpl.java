package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.AccountStatus;
import com.shop.storix.portalapi.service.auth.LoginCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginCheckServiceImpl implements LoginCheckService {
    private final LoginMapper loginMapper;
    private static final int MAX_FAIL_COUNT = 5;  // Hack: 임시 설정

    @Override
    public void handleLoginSuccess(String loginId) {
        loginMapper.resetFailCount(loginId);
    }

    @Override
    public void handleLoginFailure(String loginId, int failCount) {
        loginMapper.increaseFailCount(loginId);

        // +1 한 값이 5 이상이면 잠금
        if (failCount + 1 >= MAX_FAIL_COUNT) {
            loginMapper.updateUserLoginStatus(loginId, AccountStatus.LOCKED);
        }

    }
}
