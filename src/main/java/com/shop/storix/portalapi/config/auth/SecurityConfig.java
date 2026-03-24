package com.shop.storix.portalapi.config.auth;

import com.shop.storix.portalapi.config.auth.filter.*;
import com.shop.storix.portalapi.config.auth.handler.FailureHandler;
import com.shop.storix.portalapi.config.auth.handler.SuccessHandler;
import com.shop.storix.portalapi.service.auth.LoginCheckService;
import com.shop.storix.portalapi.service.auth.facade.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig {

    public static final String PERMITTED_URI[] = {
            "/login/oauth2/code/google",
            "/login/oauth2/code/naver",
            "/login",
            "/v3/**", "/api/v1/auth/**",
            "/oauth2/**", "/api/v1/login/**",
            "/api/v1/admin/**", "/swagger-ui/**",
            "/api/v1/option/**"
    };
    private final CustomOAuth2LoginService customOAuth2LoginService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final LocalLoginService localLoginService;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;
    private final JwtProvider jwtProvider;
    private final LoginCheckService loginCheckService;
    private final PasswordEncoder passwordEncoder;

    //HACK: filterChain 강화해주세요.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //.exceptionHandling(error -> error.authenticationEntryPoint(authenticationEntryPoint))
                .exceptionHandling(error -> error
                        .defaultAuthenticationEntryPointFor(
                                authenticationEntryPoint,
                                new NegatedRequestMatcher(new AndRequestMatcher(
                                        PathPatternRequestMatcher.pathPattern("/login"),
                                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                                ))
                        )
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PERMITTED_URI).permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/api/v1/purchaser/**").hasRole("PURCHASER")
                                .anyRequest().authenticated()
                                //.anyRequest().permitAll() // 임시
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .userInfoEndpoint(loginInfo -> loginInfo.userService(customOAuth2LoginService))
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    //HACK: cors 설정 (react url 임시방편으로 localhost:3000 으로 설정해두었습니다.)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /* 기본 인증 검증 및 기본 provider */
    @Bean
    public AuthenticationManager authenticationManager() {
        CustomAuthenticationProvider provider =
                new CustomAuthenticationProvider(localLoginService, loginCheckService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    /*Custom 로그인 필터*/
    @Bean
    public JsonLoginAuthenticationFilter jsonLoginFilter() {
        JsonLoginAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonLoginAuthenticationFilter();
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(successHandler);
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(failureHandler);
        return jsonUsernamePasswordLoginFilter;
    }

    /*Custom Jwt 인증 필터 */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    /*기본 Role Prefix 제거*/
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
