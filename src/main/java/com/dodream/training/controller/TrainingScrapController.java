package com.dodream.training.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.training.application.TrainingScrapService;
import com.dodream.training.controller.swagger.TrainingScrapSwagger;
import com.dodream.training.domain.TrainingType;
import com.dodream.training.dto.request.TrainingSaveReqeustDto;
import com.dodream.training.dto.response.scrap.TrainingScrapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/training/saved")
@RequiredArgsConstructor
public class TrainingScrapController implements TrainingScrapSwagger {

    private final TrainingScrapService trainingScrapService;

    @Override
    @PostMapping("")
    public ResponseEntity<RestResponse<TrainingScrapResponseDto>> saveTrainingPost(
            @RequestBody TrainingSaveReqeustDto trainingSaveReqeustDto
    ) {
        if(TrainingType.BOOTCAMP.getDescription().equals(trainingSaveReqeustDto.type())){
            return ResponseEntity.ok(
                    new RestResponse<>(
                            trainingScrapService.saveTraining(trainingSaveReqeustDto, TrainingType.BOOTCAMP)
                    )
            );
        }else{
            return ResponseEntity.ok(
                    new RestResponse<>(
                            trainingScrapService.saveTraining(trainingSaveReqeustDto, TrainingType.DUAL)
                    )
            );
        }
    }

}
