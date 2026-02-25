package com.shop.storix.portalapi.model.dto.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.Login;
import com.shop.storix.portalapi.model.dto.auth.domain.OAuthLogin;
import com.shop.storix.portalapi.model.dto.auth.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails , OAuth2User {
    private Login login;
    private OAuthLogin oAuthLogin;
    private String key;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Login login, Collection<Role> roles) {
        this.login = login;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.roleNm()))
                .toList();
    }

    public UserPrincipal(Login login,
                         OAuthLogin oAuthLogin,
                         Collection<Role> roles,
                         Map<String, Object> attributes,
                         String nameAttributeKey) {

        this.login = login;
        this.oAuthLogin = oAuthLogin;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.roleNm()))
                .toList();
        this.attributes = attributes;
        this.key = nameAttributeKey;
    }

    /**
     * OAuth2User method implements
     */
    @Override
    public String getName() {
        return oAuthLogin.providerId();
    }

    /**
     * UserDetails method implements
     */
    @Override
    public String getPassword() {
        return login.pw();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return String.valueOf(login.userLoginNo());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
