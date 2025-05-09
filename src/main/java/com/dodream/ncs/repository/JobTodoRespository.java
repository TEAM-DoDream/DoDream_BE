package com.dodream.ncs.repository;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTodoRespository  extends JpaRepository<JobTodo, Integer> {

    List<JobTodo> findAllByJob(Job job);
}
