package com.dodream.job.presentation.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.job.application.JobRecommendService;
import com.dodream.job.application.JobService;
import com.dodream.job.domain.TodoCategory;
import com.dodream.job.dto.response.JobTodoListResponseDto;
import com.dodream.job.dto.response.JobTodoResponseDto;
import com.dodream.job.presentation.swagger.JobSwagger;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.dto.response.JobAddListDto;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.dto.response.JobResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        @RequestParam
        int pageNum,

        @RequestParam(required = false)
        String require,

        @RequestParam(required = false)
        String workTime,

        @RequestParam(required = false)
        String physical
    ) {
        return ResponseEntity.ok(
            new RestResponse<>(jobService.getAllJobs(pageNum, require, workTime, physical))
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

    @Override
    @GetMapping("/add")
    public ResponseEntity<RestResponse<List<JobAddListDto>>> getJobAddList(

    ) {
        return ResponseEntity.ok(
            new RestResponse<>(
                jobService.getAddJobList()
            )
        );
    }

    @Override
    @GetMapping("/todo")
    public ResponseEntity<RestResponse<JobTodoListResponseDto>> getJobTodoList(
        @RequestParam("id") Long id,
        @RequestParam("todoCategory") TodoCategory todoCategory) {
        return ResponseEntity.ok(new RestResponse<>(jobService.getJodTodoList(id, todoCategory)));
    }
}
