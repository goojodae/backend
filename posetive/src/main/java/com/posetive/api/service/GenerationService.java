package com.posetive.api.service;

import com.posetive.api.repository.GenerationRepository;
import com.posetive.api.repository.UserRepository;
import com.posetive.dto.request.generation.PgpgGenerationReq;
import com.posetive.dto.response.generation.PgpgGenerationRes;
import com.posetive.entity.Generation;
import com.posetive.entity.GenerationModel;
import com.posetive.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;
    private final UserRepository userRepository;

    public PgpgGenerationRes pgpgGeneration(PgpgGenerationReq pgpgGenerationReq, Long userId) {
        String conditionImageUrl  = pgpgGenerationReq.getConditionImageUrl();
        String targetImageUrl = pgpgGenerationReq.getTargetImageUrl();

        String resultImageUrl = "http://via.placeholder.com/640x480";

        User user = userRepository.findById(userId).orElse(null);
        Generation generation = Generation.builder()
                .registeredDate(LocalDateTime.now())
                .conditionalImageUrl(conditionImageUrl)
                .targetImageUrl(targetImageUrl)
                .resultImageUrl(resultImageUrl)
                .generationModel(GenerationModel.PGPG)
                .user(user)
                .build();
        generationRepository.save(generation);

        PgpgGenerationRes pgpgGenerationRes = PgpgGenerationRes.builder()
                .resultImgageUrl(resultImageUrl)
                .build();

        return pgpgGenerationRes;
    }
}
