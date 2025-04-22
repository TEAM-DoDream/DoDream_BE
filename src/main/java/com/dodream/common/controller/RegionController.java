package com.dodream.common.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.common.application.RegionService;
import com.dodream.common.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/large")
    public ResponseEntity<RestResponse<CommonResponse.SrchList>> getLargeRegions(){
        CommonResponse.SrchList result = regionService.getLargeRegionCode();
        return ResponseEntity.ok(new RestResponse<>(result));
    }

    @GetMapping("/middle")
    public ResponseEntity<RestResponse<List<CommonResponse.ScnItem>>> getMiddleRegions(){
        List<CommonResponse.ScnItem> result = regionService.getMiddleRegion();
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
