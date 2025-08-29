package com.dodream.todo.repository;

import com.dodream.job.domain.Job;
import com.dodream.member.domain.Level;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.dto.response.TodoCommunityResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface TodoRepositoryCustom {

    List<TodoCommunityResponse> findByTop5SaveTodo(String jobName);

    Slice<TodoCommunityResponse> findTodosWithSlice(Long memberId, String jobName, Level level, String sort, Pageable pageable);

    List<Long> findMyTodoIds(Long memberId);

    Job findJobWithMostTodos();

    Todo findRandomTodo(String jobName);
}
