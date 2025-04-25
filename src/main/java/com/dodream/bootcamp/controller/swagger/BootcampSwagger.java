package com.dodream.bootcamp.controller.swagger;

import com.dodream.bootcamp.dto.response.BootcampDetailApiResponse;
import com.dodream.bootcamp.dto.response.BootcampListApiResponse;
import com.dodream.bootcamp.exception.BootcampErrorCode;
import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Bootcamp", description = "국민내일배움카드 훈련과정 목록 관련 API")
public interface BootcampSwagger {

    @Operation(
            summary = "국민내일배움카드 훈련과정 목록 반환 API",
            description = "지역 정보와 NCS 직무 이름 정보에 맞는 국민내일배움카드 훈련과정 목록을 반환합니다.",
            operationId = "/v1/bootcamp/list"
    )
    @ApiErrorCode(BootcampErrorCode.class)
    ResponseEntity<RestResponse<BootcampListApiResponse>> getBootcampList(
            @RequestParam String pageNum,
            @RequestParam(required = false) String regionName,
            @RequestParam(required = false) String ncsName
    );

    @Operation(
            summary = "국민내일배움카드 훈련과정 세부정보 반환 API",
            description = "국민내일배움카드 훈련과정 상세정보를 반환합니다.",
            operationId = "/v1/bootcamp/detail"
    )
    @ApiErrorCode(BootcampErrorCode.class)
    ResponseEntity<RestResponse<BootcampDetailApiResponse>> getBootcampDetail(
            @RequestParam String srchTrprId,
            @RequestParam String srchTrprDegr,
            @RequestParam String srchTorgId
    );
}
