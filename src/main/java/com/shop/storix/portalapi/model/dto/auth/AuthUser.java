package com.shop.storix.portalapi.model.dto.auth;

import com.shop.storix.portalapi.model.dto.auth.domain.Login;
import com.shop.storix.portalapi.model.dto.auth.domain.Role;

import java.util.List;
import java.util.Set;

public record AuthUser(Login login, List<Role> roles) {
}
