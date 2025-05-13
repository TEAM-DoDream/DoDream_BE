package com.dodream.job.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.job.application.JobRecommendService;
import com.dodream.job.application.JobService;
import com.dodream.job.controller.swagger.JobSwagger;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/job")
@RequiredArgsConstructor
public class JobController implements JobSwagger {

    private final JobRecommendService jobRecommendService;
    private final JobService jobService;

    @Override
    @GetMapping("/detail/{id}")
    public ResponseEntity<RestResponse<JobResponseDto>> getJobDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(jobService.getJobById(id))
        );
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<RestResponse<Page<JobListDto>>> getJobList(
            @RequestParam int pageNum
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(jobService.getAllJobs(pageNum))
        );
    }

    @Override
    @PostMapping("/recommend")
    public ResponseEntity<RestResponse<JobRecommendationResponse>> recommendJobsForUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody OnboardingAnswerSet onboardingAnswerSet
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                        jobRecommendService.recommendJob(customUserDetails, onboardingAnswerSet)
                )
        );
    }
}
