package com.dodream.training.presentation.swagger;

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

@Tag(name="Training", description = "배움터 찾기 관련 API")
public interface TrainingSwagger {

    @Operation(
            summary = "배움터 찾기 훈련과정 목록 반환 API",
            description = "배움터 찾기 내 필터를 사용하여 훈련 과정을 검색하는 API",
            operationId = "/v1/training/list"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingListApiResponse>> getBootcampList(
            @RequestParam
            @Parameter(description = "검색 페이지 번호(1부터 시작하는 정수값)", example = "1")
            String pageNum,

            @RequestParam(required = false, defaultValue = "이론 위주")
            @Parameter(description = "훈련방식 필터(null 입력시 이론 위주)", example = "이론 위주")
            String type,

            @RequestParam(required = false)
            @Parameter(description = "/v1/region/all을 요청시 나오는 지역 목록의 이름", example = "경기 안양시 만안구")
            String regionName,

            @RequestParam(required = false)
            @Parameter(description = "/v1/job/list를 호출하여 나오는 직업의 이름", example = "요양보호사")
            String jobName
    );

    @Operation(
            summary = "배움터 찾기 훈련과정 세부사항 반환 API",
            description = "배움터 찾기 내 검색 이후 훈련과정의 상세 정보를 검색하는 API",
            operationId = "/v1/training/detail"
    )
    @ApiErrorCode(TrainingErrorCode.class)
    ResponseEntity<RestResponse<TrainingDetailApiResponse>> getBootcampDetail(
            @RequestParam
            @Parameter(description = "훈련 방식(null 입력시 이론 위주)", example = "이론 위주")
            String type,

            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trgrId",
                    example = "AIG20240000461129"
            )
            String srchTrprId,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trprDegr(차수 번호(String))",
                    example = "4"
            )
            String srchTrprDegr,
            @RequestParam
            @Parameter(
                    description = "훈련과정 ID /v1/training/bootcamp/list 실행시 나오는 trainstCstId",
                    example = "500034772296"
            )
            String srchTorgId
    );
}
