package com.dodream.job.repository;

import com.dodream.job.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobName(String jobName);

    @Query(value = """
        SELECT j.*
        FROM job j
        JOIN (
            SELECT job_id
            FROM todo_group
            GROUP BY job_id
            ORDER BY COUNT(*) DESC
            LIMIT 1
        ) AS top_job ON j.id = top_job.job_id
    """, nativeQuery = true)
    Optional<Job> findMostPopularJob();

    @Query(value = """
        SELECT j.*
        FROM job j
        JOIN (
            SELECT job_id
            FROM todo_group
            GROUP BY job_id
            ORDER BY COUNT(*) DESC
            LIMIT 3
        ) AS top_job ON j.id = top_job.job_id
    """, nativeQuery = true)
    List<Job> findTop3PopularJob();
}
