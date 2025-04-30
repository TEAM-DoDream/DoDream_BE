package com.dodream.training.controller.swagger;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.training.exception.TrainingErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            @RequestParam
            @Parameter(description = "검색 페이지 번호(1부터 시작하는 정수값)", example = "1")
            String pageNum,
            @RequestParam(required = false)
            @Parameter(description = "/v1/region/all을 요청시 나오는 지역 목록의 이름", example = "경기 안양시 만안구")
            String regionName,
            @RequestParam(required = false)
            @Parameter(description = "/v1/ncs/all을 호출하여 나오는 직무 코드의 이름", example = "보건")
            String ncsName
    );

    @Operation(
            summary = "국민내일배움카드 훈련과정 세부정보 반환 API",
            description = "국민내일배움카드 훈련과정 상세정보를 반환합니다.",
            operationId = "/v1/training/bootcamp/detail"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingDetailApiResponse>> getBootcampDetail(
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trgrId",
                    example = "AIG20240000469334"
            )
            String srchTrprId,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trprDegr(차수 번호(String))",
                    example = "3"
            )
            String srchTrprDegr,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trainstCstId",
                    example = "500041590848"
            )
            String srchTorgId
    );

    @Operation(
            summary = "일병행학습 훈련과정 목록 반환 API",
            description = "지역 정보와 NCS 직무 이름 정보에 맞는 일병행학습 훈련과정 목록을 반환합니다.",
            operationId = "/v1/training/dual/list"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingListApiResponse>> getDualTrainingList(
            @RequestParam
            @Parameter(description = "검색 페이지 번호(1부터 시작하는 정수값)", example = "1")
            String pageNum,
            @RequestParam(required = false)
            @Parameter(description = "/v1/region/all을 요청시 나오는 지역 목록의 이름", example = "경기 안양시 만안구")
            String regionName,
            @RequestParam(required = false)
            @Parameter(description = "/v1/ncs/all을 호출하여 나오는 직무 코드의 이름", example = "보건")
            String ncsName
    );

    @Operation(
            summary = "일병행학습 훈련과정 세부정보 반환 API",
            description = "일병행학습 훈련과정 상세정보를 반환합니다.",
            operationId = "/v1/training/dual/detail"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingDetailApiResponse>> getDualTrainingDetail(
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trgrId",
                    example = "ABF20253001089849"
            )
            String srchTrprId,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trprDegr(차수 번호(String))",
                    example = "1"
            )
            String srchTrprDegr,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trainstCstId",
                    example = "500020058691"
            )
            String srchTorgId
    );
}
