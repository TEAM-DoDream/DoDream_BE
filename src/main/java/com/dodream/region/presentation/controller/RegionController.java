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
            @PathVariable String name
    ){
        return ResponseEntity.ok(new RestResponse<>(regionService.findByName(name)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<RestResponse<RegionResponseDto>> getRegionByCode(
            @PathVariable String code
    ){
        return ResponseEntity.ok(new RestResponse<>(regionService.findByRegionCode(code)));
    }
}
