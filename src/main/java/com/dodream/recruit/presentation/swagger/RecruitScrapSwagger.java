package com.dodream.recruit.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.dto.response.scrap.RecruitSavedResponseDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name="Scrap", description = "공고 및 훈련과정 스크랩 관련 컨트롤러")
public interface RecruitScrapSwagger {

    @Operation(
            summary = "채용 공고 스크랩 컨트롤러",
            description = "채용 공고를 저장합니다. (로그인 필요, 중복 저장 금지 적용)",
            operationId = "/v1/recruit/saved/{recruitId}"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitSavedResponseDto>> saveRecruitPost(
            @PathVariable String recruitId
    );
}
