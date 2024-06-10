package com.posetive.dto.request.generation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PgpgGenerationReq {
    private String conditionImageUrl;
    private String targetImageUrl;
}
