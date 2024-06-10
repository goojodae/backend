package com.posetive.api.controller;

import com.posetive.api.service.UserService;
import com.posetive.dto.response.ApiResponse;
import com.posetive.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdraw() {
        Long userId = jwtUtil.getUserId();
        userService.withdrawUser(userId);
        return ResponseEntity.ok().body(new ApiResponse<>(200, "회원 탈퇴 완료", null));
    }
}
