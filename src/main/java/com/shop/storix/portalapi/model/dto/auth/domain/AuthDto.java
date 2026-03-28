package com.shop.storix.portalapi.model.dto.auth.domain;

import com.shop.storix.portalapi.model.dto.auth.AccountStatus;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.util.List;

public class AuthDto {
    private AuthDto() {}

    @Alias("Login")
    public record Login
            (String userLoginNo,
             String id,
             String password,
             AccountStatus active,
             int failCount){}

    @Alias("OAuthLogin")
    public record OAuthLogin
            (
                    String userOauthNo,
                    String userLoginNo,
                    String provider,
                    String providerId
            ){}

    @Alias("SignInRequest")
    public record SignInRequest(
            @NotBlank String id,
            @NotBlank String password) {
    }

    @Alias("SignUpRequest")
    public record SignUpRequest(
            @Schema(description = "사용자 아이디", example = "signup11")
            @NotBlank String id,

            @Schema(description = "비밀번호", example = "a123n456!")
            @NotBlank String password,

            @Schema(description = "닉네임", example = "얍얍얍")
            String nickname,

            @Schema(description = "휴대폰 번호", example = "01012345678")
            String phoneNumber,

            @Schema(description = "사용자 email", example = "ddd@naver.com")
            String email) {
    }

    @Alias("Role")
    public record Role (
            String roleCd,
            String roleNm) {}

    @Alias("Token")
    public record Token(String userLoginNo, String refreshToken) {
    }

    @Alias("UserRole")
    public record UserRole(
            String userLoginNo,
            String roleCd)
    {}

    public record JwtPrincipal(String userLoginNo, List<String> roles) {
    }

    public record AuthUser(Login login, List<Role> roles) {
    }

    public record TokenResult(TokenStatus status, Claims claims) {}

}
