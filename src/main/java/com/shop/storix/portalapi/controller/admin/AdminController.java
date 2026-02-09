package com.shop.storix.portalapi.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/getUser")
    public ApiResponse<List<AdminUserDto>> getUser() {
        List<AdminUserDto> userList = new ArrayList<>();

        userList.add(AdminUserDto.builder()
                .id(1L)
                .username("조민성")
                .email("minsdev@storix.com")
                .role("SUPER_ADMIN")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build());

        userList.add(AdminUserDto.builder()
                .id(2L)
                .username("김우원")
                .email("kim@storix.com")
                .role("MANAGER")
                .createdAt(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME))
                .build());

        userList.add(AdminUserDto.builder()
                .id(3L)
                .username("이제희")
                .email("lee@storix.com")
                .role("STAFF")
                .createdAt(LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ISO_DATE_TIME))
                .build());

        return ApiResponse.ok(userList);
    }
}