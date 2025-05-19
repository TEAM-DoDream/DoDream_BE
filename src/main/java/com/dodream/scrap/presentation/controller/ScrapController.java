package com.dodream.scrap.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.application.save.RecruitScrapService;
import com.dodream.scrap.application.save.TrainingScrapService;
import com.dodream.scrap.domain.value.TrainingType;
import com.dodream.scrap.dto.request.TrainingSaveReqeustDto;
import com.dodream.scrap.dto.response.RecruitSavedResponseDto;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.scrap.presentation.swagger.ScrapSwagger;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/scrap")
@RequiredArgsConstructor
@Validated
public class ScrapController implements ScrapSwagger {

    private final RecruitScrapService recruitScrapService;
    private final TrainingScrapService trainingScrapService;

    @Override
    @PostMapping("/recruit/{recruitId}")
    public ResponseEntity<RestResponse<RecruitSavedResponseDto>> saveRecruitPost(
            @PathVariable String recruitId
    ) {

        return ResponseEntity.ok(
                new RestResponse<>(recruitScrapService.saveRecruit(recruitId))
        );
    }

    @Override
    @PostMapping("/training")
    public ResponseEntity<RestResponse<TrainingScrapResponseDto>> saveTrainingPost(
            @RequestBody TrainingSaveReqeustDto request,

            @RequestParam
            @Pattern(regexp = "^(이론 위주|실습 위주)$", message = "이론 위주, 실습 위주 값만 입력 가능합니다.")
            String type
    ) {

        if(TrainingType.BOOTCAMP.getDescription().equals(type)) {
            return ResponseEntity.ok(
                    new RestResponse<>(trainingScrapService.saveTraining(request, TrainingType.BOOTCAMP))
            );
        }else{
            return ResponseEntity.ok(
                    new RestResponse<>(trainingScrapService.saveTraining(request, TrainingType.DUAL))
            );
        }
    }
}
