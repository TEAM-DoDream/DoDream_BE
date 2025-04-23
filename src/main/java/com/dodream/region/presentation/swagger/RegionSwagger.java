package com.dodream.region.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.region.dto.response.RegionResponseDto;
import com.dodream.region.exception.RegionErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name="Region", description = "지역 코드/이름(고용24) 관련 검색 API")
public interface RegionSwagger {

    @Operation(
            summary = "지역 전체 리스트 검색 API",
            description = "고용24에서 제공하는 모든 지역의 지역 코드 및 이름을 검색합니다.",
            operationId = "/v1/region/all"
    )
    @ApiErrorCode(RegionErrorCode.class)
    ResponseEntity<RestResponse<List<RegionResponseDto>>> getAllRegions();

    @Operation(
            summary = "지역 이름으로 검색하는 API",
            description = "지역 이름으로 고용 24에서 제공하는 지역 코드 및 이름을 검색합니다",
            operationId = "/v1/region/name/{name}"
    )
    @ApiErrorCode(RegionErrorCode.class)
    ResponseEntity<RestResponse<RegionResponseDto>> getRegionByName(
            @Parameter(name = "name", description = "지역명 (예시: 경기 안양시 만안구)", example = "서울 종로구")
            @NotBlank(message = "지역명은 필수입니다") String name
    );

    @Operation(
            summary = "지역 코드로 검색하는 API",
            description = "지역 코드로 고용 24에서 제공하는 지역 코드 및 이름을 검색합니다",
            operationId = "/v1/region/code/{code}"
    )
    @ApiErrorCode(RegionErrorCode.class)
    ResponseEntity<RestResponse<RegionResponseDto>> getRegionByCode(
            @Parameter(name = "code", description = "지역 코드(5자리 정수(String))", example = "11110")
            @Pattern(regexp = "^[0-9]{5}$", message = "지역 코드는 5자리 숫자여야 합니다") String code
    );

}
