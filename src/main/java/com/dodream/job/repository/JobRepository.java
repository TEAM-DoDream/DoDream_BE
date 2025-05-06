package com.dodream.job.repository;

import com.dodream.job.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobName(String jobName);
}
