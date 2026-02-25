package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.domain.Login;
import com.shop.storix.portalapi.model.dto.auth.domain.PurchaserProfile;
import com.shop.storix.portalapi.model.dto.auth.domain.UserRole;
import com.shop.storix.portalapi.model.dto.auth.request.SignUpRequest;
import com.shop.storix.portalapi.service.auth.RegisterService;
import com.shop.storix.portalapi.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(SignUpRequest dto) {
        int chkVal = loginMapper.countDuplicateUser(dto.id(), dto.phoneNumber());
        if (chkVal > 0) {
           // throw new Exception(ErrorCode.DUPLICATE_USER);
        }
        // 키 생성
        String userLoginNo = UuidUtil.randomUuid();          // VARCHAR(36)
        String purchaserProfileNo = UuidUtil.randomUuid();   // VARCHAR(36)
        // 암호화
        String encodedPw = passwordEncoder.encode(dto.pw());

        Login login = new Login(userLoginNo, dto.id(), encodedPw, "ACTIVE");
        loginMapper.insertUserLogin(login);

        UserRole userRole = new UserRole(userLoginNo, "ROLE_001");// HACK: 임시로 역할 넣는 거 구현
        loginMapper.insertUserRole(userRole);

        PurchaserProfile purchaser = new PurchaserProfile(
                purchaserProfileNo,
                userLoginNo,
                dto.nickname(),
                dto.phoneNumber());

        loginMapper.insertPurchaserProfile(purchaser);
    }
}
