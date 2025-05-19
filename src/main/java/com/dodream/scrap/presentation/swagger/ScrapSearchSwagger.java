package com.dodream.scrap.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.dto.response.RecruitScrapResponseDto;
import com.dodream.scrap.exception.ScrapErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Scrap", description = "공고 및 훈련과정 스크랩 관련 컨트롤러")
public interface ScrapSearchSwagger {

    @Operation(
            summary = "스크랩한 공고 보기 api",
            description = "유저가 스크랩한 공고 리스트를 반환합니다.",
            operationId = "/v1/scrap/recruit/list"
    )
    @ApiErrorCode(ScrapErrorCode.class)
    ResponseEntity<RestResponse<Page<RecruitScrapResponseDto>>> searchScrapList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,

            @RequestParam
            @Parameter(description = "정렬 방식(최신 순, 오래된 순)(default = 최신 순)", example = "최신 순")
            String sortBy,

            @RequestParam
            @Parameter(description = "페이지 번호", example = "0")
            int pageNum,

            @RequestParam(required = false)
            @Parameter(description = "스크랩한 공고 내 지역 선택 필터(null 가능)", example = "경기 안양시 만안구")
            String locName
    );
}
