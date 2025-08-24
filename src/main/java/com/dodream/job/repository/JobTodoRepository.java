package com.dodream.job.repository;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.TodoCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTodoRepository extends JpaRepository<JobTodo, Long> {

    List<JobTodo> findAllByJob(Job job);

    List<JobTodo> findByJobAndTodoCategory(Job job, TodoCategory todoCategory);
}
