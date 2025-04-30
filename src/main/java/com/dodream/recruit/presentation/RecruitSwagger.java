package com.dodream.recruit.presentation;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import io.swagger.v3.oas.annotations.Operation;
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
            @RequestParam int pageNum,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String locationCode
    );

    @Operation(
            summary = "사람인 채용 상세 정보 반환 API",
            description = "각 공고별 상세 정보를 불러옵니다.",
            operationId = "/v1/recruit/detail"
    )
    @ApiErrorCode(RecruitErrorCode.class)
    ResponseEntity<RestResponse<RecruitResponseDetailDto>> getRecruitDetailController(
            @RequestParam String id
    );
}
