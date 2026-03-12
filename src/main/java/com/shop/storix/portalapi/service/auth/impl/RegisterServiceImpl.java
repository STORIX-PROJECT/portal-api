package com.shop.storix.portalapi.service.auth.impl;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.mapper.purchaser.PurchaserMapper;
import com.shop.storix.portalapi.model.dto.auth.AccountStatus;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.model.dto.purchaser.domain.PurchaserDto;
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
    private final PurchaserMapper purchaserMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(AuthDto.SignUpRequest dto) {
        int chkVal = loginMapper.countDuplicateUser(dto.id(), dto.phoneNumber());
        if (chkVal > 0) {
           // throw new Exception(ErrorCode.DUPLICATE_USER);
        }
        // 키 생성
        String userLoginNo = UuidUtil.randomUuid();          // VARCHAR(36)
        String purchaserProfileNo = UuidUtil.randomUuid();   // VARCHAR(36)
        // 암호화
        String encodedPw = passwordEncoder.encode(dto.password());

        AuthDto.Login login = new AuthDto.Login(userLoginNo, dto.id(), encodedPw, AccountStatus.ACTIVE,0);
        loginMapper.insertUserLogin(login);

        AuthDto.UserRole userRole = new AuthDto.UserRole(userLoginNo, "ROLE_001");// HACK: 임시로 역할 넣는 거 구현
        loginMapper.insertUserRole(userRole);

        PurchaserDto.Purchaser purchaserDto = new PurchaserDto.Purchaser(
                purchaserProfileNo,
                userLoginNo,
                dto.nickname(),
                dto.phoneNumber());

        purchaserMapper.insertPurchaserProfile(purchaserDto);
    }
}
