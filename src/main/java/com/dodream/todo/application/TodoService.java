package com.dodream.todo.application;

import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobTodoRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final MemberAuthService memberAuthService;
    private final TodoRepository todoRepository;
    private final TodoGroupRepository todoGroupRepository;


    @Transactional(readOnly = true)
    public List<GetOthersTodoGroupResponseDto> getOneTodoGroupAtHome() {

        Member member = memberAuthService.getCurrentMember();

        Optional<TodoGroup> todoGroup = todoGroupRepository.findFirstByMemberOrderByIdAsc(member);

        if (todoGroup.isEmpty()) {
            return Collections.emptyList();
        }

        Pageable limit3 = PageRequest.of(0, 3);
        List<TodoGroup> todoGroups = todoGroupRepository.findTop3ByJobAndNotMember(
            todoGroup.get().getJob(), member, limit3);

        Pageable top2 = PageRequest.of(0, 2);
        return todoGroups.stream()
            .map(group -> {
                List<Todo> todos = todoRepository.findTop2ByTodoGroup(group, top2);
                Long todoCount = todoRepository.countByTodoGroup(group);
                return GetOthersTodoGroupResponseDto.of(group, todos, todoCount);
            })
            .toList();
    }

}
