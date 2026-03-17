package com.shop.storix.portalapi.model.dto.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails , OAuth2User {
    private AuthDto.Login login;
    private AuthDto.OAuthLogin oAuthLoginDto;
    private String key;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(AuthDto.Login login, Collection<AuthDto.Role> roles) {
        this.login = login;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.roleNm()))
                .toList();
    }

    public UserPrincipal(AuthDto.Login login,
                         AuthDto.OAuthLogin oAuthLoginDto,
                         Collection<AuthDto.Role> roles,
                         Map<String, Object> attributes,
                         String nameAttributeKey) {

        this.login = login;
        this.oAuthLoginDto = oAuthLoginDto;
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
        return oAuthLoginDto.providerId();
    }

    /**
     * UserDetails method implements
     */
    @Override
    public String getPassword() {
        return login.password();
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
        return login.active() != AccountStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return login.active() != AccountStatus.INACTIVE;
    }
}
