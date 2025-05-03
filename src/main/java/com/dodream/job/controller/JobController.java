package com.dodream.job.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.job.application.JobService;
import com.dodream.job.controller.swagger.JobSwagger;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/job")
@RequiredArgsConstructor
public class JobController implements JobSwagger {

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
    public ResponseEntity<RestResponse<List<JobListDto>>> getJobList() {
        return ResponseEntity.ok(
                new RestResponse<>(jobService.getAllJobs())
        );
    }
}
