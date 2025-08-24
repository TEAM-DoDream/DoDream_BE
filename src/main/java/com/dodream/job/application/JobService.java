package com.dodream.job.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.TodoCategory;
import com.dodream.job.dto.response.JobAddListDto;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import com.dodream.job.dto.response.JobTodoListResponseDto;
import com.dodream.job.dto.response.JobTodoResponseDto;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobSpecification;
import com.dodream.job.repository.JobTodoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final JobImageUrlGenerator imageUrlGenerator;
    private final JobTodoRepository jobTodoRepository;

    public JobResponseDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(
            JobErrorCode.CANNOT_GET_JOB_DATA::toException
        );

        return JobResponseDto.from(job);
    }

    public Page<JobListDto> getAllJobs(int pageNumber, String require, String workTime,
        String physical) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        Pageable pageable = PageRequest.of(pageNumber, 9);
        Specification<Job> spec = JobSpecification.matchesFilter(require, workTime, physical);
        Page<Job> jobs = jobRepository.findAll(spec, pageable);

        return jobs.map(job -> JobListDto.from(job, imageUrlGenerator));
    }

    public List<JobAddListDto> getAddJobList() {
        return jobRepository.findAll()
            .stream()
            .map(job -> JobAddListDto.from(job, imageUrlGenerator))
            .toList();
    }

    public JobTodoListResponseDto getJodTodoList(Long id, TodoCategory todoCategory) {

        Job job = jobRepository.findById(id).orElseThrow(
            JobErrorCode.CANNOT_GET_JOB_DATA::toException
        );

        List<JobTodoResponseDto> jobTodos = jobTodoRepository.findByJobAndTodoCategory(job,
                todoCategory).stream()
            .map(JobTodoResponseDto::from)
            .toList();

        return JobTodoListResponseDto.of(job,jobTodos,todoCategory);

    }
}
