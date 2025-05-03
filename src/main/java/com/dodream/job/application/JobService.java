package com.dodream.job.application;

import com.dodream.job.domain.Job;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<JobListDto> getAllJobs(int pageNumber) {
        if(pageNumber < 0) {
            pageNumber = 0;
        }

        Pageable pageable = PageRequest.of(pageNumber, 9);
        Page<Job> jobs = jobRepository.findAll(pageable);

        return jobs.stream()
                .map(job -> JobListDto.from(job))
                .toList();
    }
}
