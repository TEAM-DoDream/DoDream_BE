package com.dodream.scrap.presentation.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.scrap.application.ScrapDeleteService;
import com.dodream.scrap.dto.response.ScrapDeletedResponse;
import com.dodream.scrap.presentation.swagger.ScrapDeleteSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/scrap")
@RequiredArgsConstructor
public class ScrapDeleteController implements ScrapDeleteSwagger {

    private final ScrapDeleteService scrapDeleteService;

    @Override
    @DeleteMapping("/recruit/{recruitId}")
    public ResponseEntity<RestResponse<ScrapDeletedResponse>> deleteRecruitScrap(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String recruitId
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                      scrapDeleteService.deleteRecruitScrap(recruitId, customUserDetails)
                )
        );
    }

    @Override
    @DeleteMapping("/training/{trainingId}")
    public ResponseEntity<RestResponse<ScrapDeletedResponse>> deleteTrainingScrap(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String trainingId
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                        scrapDeleteService.deleteTrainingScrap(trainingId, customUserDetails)
                )
        );
    }
}
