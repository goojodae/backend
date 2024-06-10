package com.posetive.api.service;

import com.posetive.api.repository.GenerationRepository;
import com.posetive.api.repository.UserRepository;
import com.posetive.dto.request.generation.PgpgGenerationReq;
import com.posetive.dto.response.generation.MyGenerationDetailRes;
import com.posetive.dto.response.generation.MyGenerationListItem;
import com.posetive.dto.response.generation.MyGenerationListRes;
import com.posetive.dto.response.generation.PgpgGenerationRes;
import com.posetive.entity.Generation;
import com.posetive.entity.GenerationModel;
import com.posetive.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public MyGenerationListRes getMyGenerationList(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<MyGenerationListItem> myGenerationList = new ArrayList<>();
        for (Generation generation : user.getGenerations()) {
            MyGenerationListItem myGenerationListItem = MyGenerationListItem.builder()
                    .conditionImageUrl(generation.getConditionalImageUrl())
                    .targetImageUrl(generation.getTargetImageUrl())
                    .resultImgageUrl(generation.getResultImageUrl())
                    .generationModel(generation.getGenerationModel().toString().toLowerCase())
                    .build();
            myGenerationList.add(myGenerationListItem);
        }

        MyGenerationListRes myGenerationListRes = MyGenerationListRes.builder()
                .generations(myGenerationList)
                .build();

        return myGenerationListRes;
    }

    public Boolean existsById(Long generationId) {
        if (generationRepository.existsById(generationId)) {
            if (generationRepository.findById(generationId).orElse(null).getUser().getLoginId().equals("deleted")) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Boolean isOwner(Long userId, Long generationId) {
        return generationRepository.findById(generationId).orElse(null).getUser().getId().equals(userId);
    }

    public MyGenerationDetailRes getMyGenerationDetail(Long generationId) {
        Generation generation = generationRepository.findById(generationId).orElse(null);

        MyGenerationDetailRes myGenerationDetailRes = MyGenerationDetailRes.builder()
                .conditionImageUrl(generation.getConditionalImageUrl())
                .targetImageUrl(generation.getTargetImageUrl())
                .resultImgageUrl(generation.getResultImageUrl())
                .generationModel(generation.getGenerationModel().toString().toLowerCase())
                .build();

        return myGenerationDetailRes;
    }
}
