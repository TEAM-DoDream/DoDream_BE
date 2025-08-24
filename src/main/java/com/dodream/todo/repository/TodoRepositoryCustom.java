package com.dodream.todo.repository;

import com.dodream.member.domain.Level;
import com.dodream.todo.dto.response.TodoCommunityResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TodoRepositoryCustom {

    List<TodoCommunityResponse> findByTop5SaveTodo(String jobName);

    Slice<TodoCommunityResponse> findTodosWithSlice(Long memberId, String jobName, Level level, String sort, Pageable pageable);

    List<Long> findMyTodoIds(Long memberId);
}
