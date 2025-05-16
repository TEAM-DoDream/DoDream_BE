package com.dodream.region.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.region.application.RegionService;
import com.dodream.region.dto.response.RegionResponseDto;
import com.dodream.region.presentation.swagger.RegionSwagger;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/region")
@RequiredArgsConstructor
@Validated
public class RegionController implements RegionSwagger {

    private final RegionService regionService;

    @GetMapping("/all")
    public ResponseEntity<RestResponse<List<RegionResponseDto>>> getAllRegions(){
        return ResponseEntity.ok(new RestResponse<>(regionService.findAllRegions()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestResponse<RegionResponseDto>> getRegionByName(
            @Parameter(name = "name", description = "지역명 (예시: 경기 안양시 만안구)", example = "서울 종로구")
            @NotBlank(message = "지역명은 필수입니다")
            @PathVariable String name
    ){
        return ResponseEntity.ok(new RestResponse<>(regionService.findByName(name)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<RestResponse<RegionResponseDto>> getRegionByCode(
            @Parameter(name = "code", description = "지역 코드(5자리 정수(String))", example = "11110")
            @Pattern(regexp = "^[0-9]{5}$", message = "지역 코드는 5자리 숫자여야 합니다")
            @PathVariable String code
    ){
        return ResponseEntity.ok(new RestResponse<>(regionService.findByRegionCode(code)));
    }
}
