package com.shop.storix.portalapi.model.dto.auth.domain;

import com.shop.storix.portalapi.common.mail.MailVariables;
import com.shop.storix.portalapi.model.dto.auth.AccountStatus;
import com.shop.storix.portalapi.model.dto.auth.TokenStatus;
import io.jsonwebtoken.Claims;
import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.Map;

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

    public record SendMailCodeRequest(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email
    ) {}

    public record VerifyMailCodeRequest(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "인증 코드는 필수입니다.")
            @Size(min = 6, max = 6, message = "인증 코드는 6자리입니다.")
            String code
    ) {}

    public record EmailAuthCode(String email, String code , MailPurpose mailPurpose) {
    }

    public record AuthCodeVariables(String code, int expireMinutes) implements MailVariables {

        private static final String TEMPLATE    = "mail/auth-code";
        private static final String SUBJECT_KEY = "mail.auth.subject";

        @Override
        public String getTemplateName() {
            return TEMPLATE;
        }

        @Override
        public String getSubjectKey() {
            return SUBJECT_KEY;
        }

        @Override
        public Map<String, Object> toMap() {
            return Map.of(
                    "code", code,
                    "expireMinutes", expireMinutes
            );
        }
    }

}
