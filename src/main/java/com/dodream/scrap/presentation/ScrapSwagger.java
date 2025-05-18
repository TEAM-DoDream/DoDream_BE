package com.dodream.scrap.presentation;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.dto.request.TrainingSaveReqeustDto;
import com.dodream.scrap.dto.response.RecruitSavedResponseDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.training.exception.TrainingErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Scrap", description = "공고 및 훈련과정 스크랩 관련 컨트롤러")
public interface ScrapSwagger {

    @Operation(
            summary = "채용 공고 스크랩 API",
            description = "채용 공고를 저장합니다. (로그인 필요, 중복 저장 금지 적용)",
            operationId = "/v1/scrap/recruit/{recruitId}"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitSavedResponseDto>> saveRecruitPost(
            @PathVariable String recruitId
    );

    @Operation(
            summary = "훈련 과정 스크랩 API",
            description = "훈련 과정을 저장합니다. (로그인 필요, 중복 저장 금지 적용)",
            operationId = "/v1/scrap/training"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingScrapResponseDto>> saveTrainingPost(
            @RequestBody TrainingSaveReqeustDto trainingSaveReqeustDto,

            @RequestParam
            @Parameter(description = "훈련 방식 (가능한 값: '이론 위주', '실습 위주')", example = "이론 위주")
            String type
    );
}
