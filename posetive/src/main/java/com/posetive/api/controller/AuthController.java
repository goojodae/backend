package com.posetive.api.controller;

import com.posetive.api.service.AuthService;
import com.posetive.api.service.UserService;
import com.posetive.dto.response.ApiResponse;
import com.posetive.dto.request.auth.RegisterUserReq;
import com.posetive.entity.User;
import com.posetive.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserReq registerUserReq, HttpServletResponse response) throws IOException {
        String loginId = registerUserReq.getLoginId();
        if (userService.existsByLoginId(loginId)) {
            return ResponseEntity.ok().body(new ApiResponse(409, "이미 해당 정보로 가입한 유저가 있습니다", null));
        }

        authService.registerUser(registerUserReq);
        User user = userService.findByLoginId(loginId);
        String accessToken = authService.loginUser(loginId, response);
        return ResponseEntity.ok().body(new ApiResponse(201, "유저 생성 완료", authService.getUserLoginInfo(user.getId(), accessToken, user.getIsSubscribed())));
    }

}
