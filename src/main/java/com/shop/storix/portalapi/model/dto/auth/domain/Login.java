package com.shop.storix.portalapi.model.dto.auth.domain;

import java.time.LocalDateTime;

public record Login
    (String userLoginNo,
     String id,
     String pw,
     String active){}
