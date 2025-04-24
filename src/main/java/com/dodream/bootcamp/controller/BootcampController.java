package com.dodream.bootcamp.controller;

import com.dodream.bootcamp.application.BootcampApiService;
import com.dodream.bootcamp.dto.response.BootcampListApiResponse;
import com.dodream.core.presentation.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bootcamp")
@RequiredArgsConstructor
public class BootcampController {

    private final BootcampApiService bootcampApiService;

    @GetMapping
    public ResponseEntity<RestResponse<BootcampListApiResponse>> getBootcampList(
        @RequestParam String pageNum,
        @RequestParam String pageSize,
        @RequestParam(required = false) String regionName,
        @RequestParam(required = false) String ncsName
    ){
        return ResponseEntity.ok(new RestResponse<>(
                bootcampApiService.getBootcampList(pageNum, pageSize, regionName, ncsName))
        );
    }
}
