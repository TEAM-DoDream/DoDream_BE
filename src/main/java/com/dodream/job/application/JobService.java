package com.dodream.job.application;

import com.dodream.job.domain.Job;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public JobResponseDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                JobErrorCode.CANNOT_GET_JOB_DATA::toException
        );

        return JobResponseDto.from(job);
    }

    public Page<JobListDto> getAllJobs(int pageNumber, String require, String workTime, String physical) {
        if(pageNumber < 0) {
            pageNumber = 0;
        }

        Pageable pageable = PageRequest.of(pageNumber, 9);
        Specification<Job> spec = JobSpecification.matchesFilter(require, workTime, physical);
        Page<Job> jobs = jobRepository.findAll(spec, pageable);

        return jobs.map(JobListDto::from);
    }
}
