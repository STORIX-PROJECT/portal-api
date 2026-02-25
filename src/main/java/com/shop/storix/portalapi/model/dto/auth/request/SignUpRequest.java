package com.shop.storix.portalapi.model.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @Schema(description = "사용자 아이디", example = "signup11")
        @NotBlank String id,

        @Schema(description = "비밀번호", example = "a123n456!")
        @NotBlank String pw,

        @Schema(description = "닉네임", example = "얍얍얍")
        String nickname,

        @Schema(description = "휴대폰 번호", example = "01012345678")
        String phoneNumber) {
}
