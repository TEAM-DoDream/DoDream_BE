package com.dodream.scrap.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.dto.response.ScrapDeletedResponse;
import com.dodream.scrap.exception.ScrapErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name="Scrap", description = "공고 및 훈련과정 스크랩 관련 컨트롤러")
public interface ScrapDeleteSwagger {

    @Operation(
            summary = "스크랩한 채용 공고 삭제 API",
            description = "스크랩한 공고를 삭제합니다.",
            operationId = "/v1/scrap/recruit/{recruitId}"
    )
    @ApiErrorCode(ScrapErrorCode.class)
    ResponseEntity<RestResponse<ScrapDeletedResponse>> deleteRecruitScrap(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,

            @PathVariable(name = "id")
            @Parameter(description = "채용공고 리스트 api 호출시 나오는 id값")
            String recruitId
    );

    @Operation(
            summary = "스크랩한 훈련 과정 삭제 API",
            description = "스크랩한 훈련 과정 공고를 삭제합니다.",
            operationId = "/v1/scrap/training/{trainingId}"
    )
    @ApiErrorCode(ScrapErrorCode.class)
    ResponseEntity<RestResponse<ScrapDeletedResponse>> deleteTrainingScrap(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,

            @PathVariable(name = "id")
            @Parameter(description = "훈련 과정 공고 검색시 나오는 trprId(훈련과정 고유값)")
            String trainingId
    );
}
