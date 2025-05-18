package com.dodream.recruit.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.recruit.application.RecruitScrapService;
import com.dodream.recruit.dto.response.scrap.RecruitSavedResponseDto;
import com.dodream.recruit.presentation.swagger.RecruitScrapSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/recruit/saved")
@RequiredArgsConstructor
public class RecruitScrapController implements RecruitScrapSwagger {

    private final RecruitScrapService recruitScrapService;

    @Override
    @GetMapping("/{recruitId}")
    public ResponseEntity<RestResponse<RecruitSavedResponseDto>> saveRecruitPost(
            @PathVariable String recruitId
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(recruitScrapService.saveRecruit(recruitId))
        );
    }
}
