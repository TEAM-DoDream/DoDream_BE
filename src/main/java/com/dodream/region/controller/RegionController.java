package com.dodream.region.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.region.application.RegionService;
import com.dodream.region.dto.response.RegionResponse;
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
    public ResponseEntity<RestResponse<RegionResponse.SrchList>> getLargeRegions(){
        RegionResponse.SrchList result = regionService.getLargeRegionCode();
        return ResponseEntity.ok(new RestResponse<>(result));
    }

    @GetMapping("/middle")
    public ResponseEntity<RestResponse<List<RegionResponse.ScnItem>>> getMiddleRegions(){
        List<RegionResponse.ScnItem> result = regionService.getMiddleRegion();
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
