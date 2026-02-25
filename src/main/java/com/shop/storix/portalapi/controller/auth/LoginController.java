package com.shop.storix.portalapi.controller.auth;


import com.shop.storix.portalapi.controller.admin.ApiResponse;
import com.shop.storix.portalapi.model.dto.auth.request.SignUpRequest;
import com.shop.storix.portalapi.service.auth.facade.AuthService;
import com.shop.storix.portalapi.service.auth.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 및 회원 관리 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final RegisterService registerService;
    private final AuthService authService;

    @Operation(
            summary = "회원가입",
            description = "사용자 정보를 받아 회원가입을 진행합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody SignUpRequest dto)
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
    public ResponseEntity<ApiResponse<?>> reissueRefreshToken (HttpServletRequest request , HttpServletResponse response)
    {
        authService.reissueToken(request,response);
        return ResponseEntity.ok(
                ApiResponse.ok("발급 완료")
        );
    }


}
