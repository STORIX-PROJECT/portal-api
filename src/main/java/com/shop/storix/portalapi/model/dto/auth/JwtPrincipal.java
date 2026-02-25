package com.shop.storix.portalapi.model.dto.auth;

import java.util.List;

public record JwtPrincipal(String userLoginNo, List<String> roles) {
}
