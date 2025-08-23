package com.dodream.todo.repository;

import com.dodream.todo.dto.response.TodoCommunityResponse;

import java.util.List;

public interface TodoRepositoryCustom {
    List<TodoCommunityResponse> findByTop5SaveTodo(String jobName);
}
