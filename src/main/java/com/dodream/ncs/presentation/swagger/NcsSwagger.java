package com.dodream.ncs.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.ncs.dto.response.NcsResponseDto;
import com.dodream.ncs.exception.NcsErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "NCS", description = "Ncs 직무 정보(고용 24)관련 검색 API")
public interface NcsSwagger {

    @Operation(
            summary = "NCS 전체 리스트 검색 API",
            description = "고용24에서 제공하는 모든 NCS 직무 코드 및 이름을 검색합니다.",
            operationId = "/v1/ncs/all"
    )
    @ApiErrorCode(NcsErrorCode.class)
    ResponseEntity<RestResponse<List<NcsResponseDto>>> getAllNcs();

    @Operation(
            summary = "NCS 직무 이름으로 검색하는 API",
            description = "NCS 직무 이름으로 고용 24에서 제공하는 NCS 직무 코드 및 이름을 검색합니다",
            operationId = "/v1/ncs/name/{name}"
    )
    @ApiErrorCode(NcsErrorCode.class)
    ResponseEntity<RestResponse<NcsResponseDto>> getNcsByName(
            @Parameter(name = "name", description = "직무 이름 (예시: 철도차량제작)", example = "화학·바이오공통")
            @NotBlank(message = "직무 이름은 필수입니다") String name
    );

    @Operation(
            summary = "NCS 코드로 검색하는 API",
            description = "NCS 직무 코드로 고용 24에서 제공하는 직무 코드 및 이름을 검색합니다",
            operationId = "/v1/ncs/code/{code}"
    )
    @ApiErrorCode(NcsErrorCode.class)
    ResponseEntity<RestResponse<NcsResponseDto>> getNcsByCode(
            @Parameter(name = "code",
                    description = "NCS 직무 코드 -> 대분류(2자리 정수), 중분류(4자리 정수), " +
                            "소분류(6자리 정수), 세분류(8자리 정수)",
                    example = "01"
            )
            @Pattern(regexp = "^(\\d{2}|\\d{4}|\\d{6}|\\d{8})$", message = "2,4,6,8자리 정수를 입력해 주세요") String code
    );
}
