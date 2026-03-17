package com.shop.storix.portalapi.config.auth.filter;

import com.shop.storix.portalapi.model.dto.auth.UserPrincipal;
import com.shop.storix.portalapi.service.auth.LoginCheckService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Consumer;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final LoginCheckService loginCheckService;

    public CustomAuthenticationProvider(LocalLoginService localLoginService,
            LoginCheckService loginCheckService) {
        super(localLoginService);
        this.loginCheckService = loginCheckService;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            // 기존 비밀번호 비교 사용
            super.additionalAuthenticationChecks(userDetails, authentication);

            checkUserDetailsType(userDetails,principal ->
                loginCheckService.handleLoginSuccess(principal.getLogin().id())
            );



        } catch (BadCredentialsException e) {
            checkUserDetailsType(userDetails,principal -> {
                int failCount = principal.getLogin().failCount();
                loginCheckService.handleLoginFailure(principal.getLogin().id(), failCount);
            });
            throw e;
        }
    }

    /*userDetails 타입 검증*/
    private void checkUserDetailsType(Object userDetails, Consumer<UserPrincipal> action) {
        if (userDetails instanceof UserPrincipal principal && principal.getLogin() != null) {
            action.accept(principal);
        }
    }
}
