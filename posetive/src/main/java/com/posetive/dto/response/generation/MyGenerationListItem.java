package com.posetive.dto.response.generation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyGenerationListItem {
    private String conditionImageUrl;
    private String targetImageUrl;
    private String resultImgageUrl;
    private String generationModel;
}
