package com.dodream.training.controller.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.training.dto.request.TrainingSaveReqeustDto;
import com.dodream.training.dto.response.scrap.TrainingScrapResponseDto;
import com.dodream.training.exception.TrainingErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name="Scrap", description = "공고 및 훈련과정 스크랩 관련 컨트롤러")
public interface TrainingScrapSwagger {

    @Operation(
            summary = "훈련과정 스크랩 API",
            description = "훈련과정을 저장합니다. (로그인 필요, 중복 저장 금지 적용)",
            operationId = "/v1/training/saved"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingScrapResponseDto>> saveTrainingPost(
            @RequestBody TrainingSaveReqeustDto trainingSaveReqeustDto
    );
}
