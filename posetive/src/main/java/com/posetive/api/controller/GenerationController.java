package com.posetive.api.controller;

import com.posetive.api.service.GenerationService;
import com.posetive.dto.request.generation.PgpgGenerationReq;
import com.posetive.dto.response.ApiResponse;
import com.posetive.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generation")
public class GenerationController {
    private final GenerationService generationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/pgpg")
    public ResponseEntity<ApiResponse> pgpgGeneration(@RequestBody PgpgGenerationReq pgpgGenerationReq) {
        Long userId = jwtUtil.getUserId();
        return ResponseEntity.ok().body(new ApiResponse<>(201, "PG2 이미지 생성 인퍼런스 성공", generationService.pgpgGeneration(pgpgGenerationReq, userId)));
    }
}
