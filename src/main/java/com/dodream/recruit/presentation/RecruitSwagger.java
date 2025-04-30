package com.dodream.recruit.presentation;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Recruit", description = "사람인 채용정보 관련 API")
public interface RecruitSwagger {

    @Operation(
            summary = "사람인 채용정보 반환 API",
            description = "키워드 및 지역 정보를 활용하여 채용정보 리스트를 불러옵니다.",
            operationId = "/v1/recruit/list"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitResponseListDto>> getRecruitListController(
            @RequestParam
            @Parameter(description = "페이지 번호(0부터 시작)", example = "0")
            int pageNum,

            @RequestParam(required = false)
            @Parameter(description = "검색을 위한 키워드", example = "요양보호사")
            String keyWord,

            @RequestParam(required = false)
            @Parameter(description = "/v1/region/all을 호출해서 나오는 지역 이름", example = "경기 안양시 만안구")
            String locationName
    );

    @Operation(
            summary = "사람인 채용 상세 정보 반환 API",
            description = "각 공고별 상세 정보를 불러옵니다.",
            operationId = "/v1/recruit/detail"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitResponseListDto>> getRecruitDetailController(
            @RequestParam
            @Parameter(description = "채용공고 검색시 나오는 id", example = "50611581")
            String id
    );
}
