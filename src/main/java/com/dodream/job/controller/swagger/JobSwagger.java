package com.dodream.job.controller.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import com.dodream.job.exception.JobErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Job-Search", description = "직업 탐색 관련 컨트롤러")
public interface JobSwagger {

    @Operation(
            summary = "직업 상세정보 반환 컨트롤러",
            description = "각 직업의 상세정보를 반환합니다.(직업 탐색 상세)",
            operationId = "/v1/job/{id}"
    )
    @ApiErrorCode(JobErrorCode.class)
    ResponseEntity<RestResponse<JobResponseDto>> getJobDetail(
            @PathVariable Long id
    );

    @Operation(
            summary = "직업 탐색 직업 반환",
            description = "직업 탐색에서 사용하는 직업 카드 데이터를 반환합니다.",
            operationId = "/v1/job/list"
    )
    @ApiErrorCode(JobErrorCode.class)
    ResponseEntity<RestResponse<Page<JobListDto>>> getJobList(
            @RequestParam
            int pageNum,

            @RequestParam(required = false)
            @Parameter(description = "자격증 필요 여부", example = "필요함")
            String require,

            @RequestParam(required = false)
            @Parameter(description = "근무시간대", example = "평일 9시-18시")
            String workTime,

            @RequestParam(required = false)
            @Parameter(description = "신체활동 정도", example = "정적인 활동")
            String physical
    );

    @Operation(
            summary = "온보딩 결과 직업 추천 API",
            description = "온보딩 결과 사용자에게 맞는 직업을 3개 추천합니다.",
            operationId = "/v1/job/recommend"
    )
    @ApiErrorCode(JobErrorCode.class)
    ResponseEntity<RestResponse<JobRecommendationResponse>> recommendJobsForUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody OnboardingAnswerSet onboardingAnswerSet
    );
}
