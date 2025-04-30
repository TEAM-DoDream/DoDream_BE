package com.dodream.training.controller.swagger;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.training.exception.TrainingErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Training", description = "국민내일배움카드 훈련과정 및 일병행학습 훈련과정 관련 API")
public interface TrainingSwagger {

    @Operation(
            summary = "국민내일배움카드 훈련과정 목록 반환 API",
            description = "지역 정보와 NCS 직무 이름 정보에 맞는 국민내일배움카드 훈련과정 목록을 반환합니다.",
            operationId = "/v1/training/bootcamp/list"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingListApiResponse>> getBootcampList(
            @RequestParam String pageNum,
            @RequestParam(required = false) String regionName,
            @RequestParam(required = false) String ncsName
    );

    @Operation(
            summary = "국민내일배움카드 훈련과정 세부정보 반환 API",
            description = "국민내일배움카드 훈련과정 상세정보를 반환합니다.",
            operationId = "/v1/training/bootcamp/detail"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingDetailApiResponse>> getBootcampDetail(
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    );

    @Operation(
            summary = "일병행학습 훈련과정 목록 반환 API",
            description = "지역 정보와 NCS 직무 이름 정보에 맞는 일병행학습 훈련과정 목록을 반환합니다.",
            operationId = "/v1/training/dual/list"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingListApiResponse>> getDualTrainingList(
            @RequestParam String pageNum,
            @RequestParam(required = false) String regionName,
            @RequestParam(required = false) String ncsName
    );

    @Operation(
            summary = "일병행학습 훈련과정 세부정보 반환 API",
            description = "일병행학습 훈련과정 상세정보를 반환합니다.",
            operationId = "/v1/training/dual/detail"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingDetailApiResponse>> getDualTrainingDetail(
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    );
}
