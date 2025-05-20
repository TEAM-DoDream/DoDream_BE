package com.dodream.scrap.presentation.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.application.ScrapSearchService;
import com.dodream.scrap.dto.response.RecruitScrapResponseDto;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.scrap.presentation.swagger.ScrapSearchSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/scrap")
@RequiredArgsConstructor
public class ScrapSearchController implements ScrapSearchSwagger {

    private final ScrapSearchService scrapSearchService;

    @Override
    @GetMapping("/recruit/list")
    public ResponseEntity<RestResponse<Page<RecruitScrapResponseDto>>> searchScrapList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) String sortBy,
            @RequestParam int pageNum,
            @RequestParam(required = false) String locName
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(scrapSearchService.getRecruitScrapList(customUserDetails, pageNum, locName, sortBy))
        );
    }

    @Override
    @GetMapping("/training/list")
    public ResponseEntity<RestResponse<Page<TrainingScrapResponseDto>>> searchTrainingScrapList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) String sortBy,
            @RequestParam int pageNum,
            @RequestParam(required = false) String locName
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(scrapSearchService.getTrainingScrapList(customUserDetails, pageNum, locName, sortBy))
        );
    }
}
