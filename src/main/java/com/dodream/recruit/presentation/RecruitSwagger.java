package com.dodream.recruit.presentation;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.dto.response.PopularRecruitResponse;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Recruit", description = "일자리 찾기 관련 API")
public interface RecruitSwagger {

    @Operation(
            summary = "일자리 찾기 리스트 반환 API",
            description = "키워드 및 지역 정보를 활용하여 채용 정보 리스트를 불러옵니다.",
            operationId = "/v1/recruit/list"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitResponseListDto>> getRecruitListController(
            @AuthenticationPrincipal
            CustomUserDetails customUserDetails,

            @RequestParam
            @Parameter(description = "페이지 번호(0부터 시작)", example = "0")
            int pageNum,

            @RequestParam(required = false)
            @Parameter(description = "검색을 위한 키워드", example = "요양보호사")
            String keyWord,

            @RequestParam
            @Parameter(
                    description = "/v1/region/all을 호출해서 나오는 지역 이름",
                    example = "경기 안양시 만안구"
            )
            String locationName,

            @RequestParam
            @DateTimeFormat(pattern = "yyyy/MM/dd")
            @Parameter(
                    description = "공고 시작일(yyyy/MM/dd)",
                    example = "2025/05/09"
            )
            String startDate,

            @RequestParam
            @DateTimeFormat(pattern = "yyyy/MM/dd")
            @Parameter(
                    description = "공고 마감일(yyyy/MM/dd)",
                    example = "2026/05/09"
            )
            String endDate,

            @RequestParam
            @Parameter(description = "정렬 기준(null인경우 마감일로 정렬)", example = "post")
            String sortBy
    );

    @Operation(
            summary = "일자리 찾기 상세 정보 반환 API",
            description = "각 공고별 상세 정보를 불러옵니다.",
            operationId = "/v1/recruit/detail"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitResponseListDto>> getRecruitDetailController(
            @RequestParam
            @Parameter(description = "채용공고 검색시 나오는 id", example = "50611581")
            String id
    );

    @Operation(
            summary = "홈화면 - 인기 직업 공고 건수 반환 API",
            description = "현재 가장 인기있는 직업의 공고 건수를 반환합니다. (홈화면 - 구인 현황(오른쪽 상단))",
            operationId = "/v1/recruit/popular"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<List<PopularRecruitResponse>>> getPopularRecruitDetailController(
            @AuthenticationPrincipal
            CustomUserDetails customUserDetails
    );
}
