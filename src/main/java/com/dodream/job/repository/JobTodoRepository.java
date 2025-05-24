package com.dodream.job.repository;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTodoRepository extends JpaRepository<JobTodo, Long> {

    List<JobTodo> findAllByJob(Job job);
}
