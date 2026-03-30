package com.shop.storix.portalapi.controller.auth;

import com.shop.storix.portalapi.common.ApiResponse;
import com.shop.storix.portalapi.model.dto.auth.MailPurpose;
import com.shop.storix.portalapi.model.dto.auth.domain.AuthDto;
import com.shop.storix.portalapi.service.auth.facade.AccountService;
import com.shop.storix.portalapi.service.auth.facade.AuthService;
import com.shop.storix.portalapi.service.auth.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Tag(name = "Auth", description = "인증 및 회원 관리 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final RegisterService registerService;
    private final AuthService authService;
    private final AccountService accountService;

    @Operation(
            summary = "회원가입",
            description = "사용자 정보를 받아 회원가입을 진행합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody AuthDto.SignUpRequest dto)
    {
        registerService.signUp(dto);

        return ResponseEntity.ok(
                ApiResponse.ok("회원가입이 완료되었습니다.")
        );
    }

    @Operation(
            summary = "토큰 재발급",
            description = "Refresh Token을 이용하여 새로운 Access Token을 발급합니다.",
            security = @SecurityRequirement(name = "refreshCookie")
    )
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<String>> reissueRefreshToken (HttpServletRequest request , HttpServletResponse response)
    {
        authService.reissueToken(request,response);
        return ResponseEntity.ok(
                ApiResponse.ok("발급 완료")
        );
    }
    @Operation(
            summary = "로그아웃",
            description = "사용자 로그아웃을 진행합니다.",
            security = @SecurityRequirement(name = "refreshCookie")
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> signOut (HttpServletRequest request , HttpServletResponse response)
    {
        authService.deleteRefreshToken(request ,response);
        return ResponseEntity.ok(
                ApiResponse.ok("삭제 완료")
        );
    }

    @Operation(
            summary = "사용자 아이디 찾기",
            description ="발급 받은 메일코드를 인증하고 해당 이메일로 사용자를 검색합니다."
    )
    @GetMapping("/lookup/id")
    public ResponseEntity<ApiResponse<String>> lookUpUserId(@ModelAttribute AuthDto.VerifyMailCodeRequest verifyMailCodeRequest)
    {
        String loginId = accountService.findUserId(verifyMailCodeRequest, MailPurpose.FIND_ID);
        return ResponseEntity.ok(
                ApiResponse.ok(loginId)
        );
    }
    @Operation(
            summary = "인증 메일 보내기",
            description ="사용자에게 인증 매일을 보냅니다."
    )
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendCode(
            @RequestParam MailPurpose mailPurpose,
            @RequestBody @Validated AuthDto.SendMailCodeRequest sendMailCodeRequest,
            Locale locale) {
        accountService.sendMail(sendMailCodeRequest, mailPurpose, locale);
        return ResponseEntity.ok(
                ApiResponse.ok("전송되었습니다.")
        );
    }





}
