package com.dodream.region.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.region.application.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<RestResponse<String>> getRegions(
            @RequestParam(name = "searchType") String searchType,
            @RequestParam(name = "searchOption1", required = false) String searchOption1,
            @RequestParam(name = "searchOption2", required = false) String searchOption2
    ){
        String result = regionService.getRegionCode(searchType, searchOption1, searchOption2);
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
