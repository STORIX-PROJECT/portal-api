package com.shop.storix.portalapi.config.auth.filter;

import com.shop.storix.portalapi.mapper.auth.LoginMapper;
import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalLoginService implements UserDetailsService {

    private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        AuthDto.Login login = loginMapper.findLoginByLoginId(id)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자인데용"));
        List<AuthDto.Role> roles = loginMapper.findUserRoleByLoginNo(login);
        return new UserPrincipal(login,roles);
    }
}
