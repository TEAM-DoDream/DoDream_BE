package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.TodoCategory;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoSimpleResponseDto;
import com.dodream.todo.dto.response.GetTodoGroupByCategoryResponseDto;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final JobRepository jobRepository;


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

    @Transactional(readOnly = true)
    public List<GetOthersTodoSimpleResponseDto> getOthersTodoSimple(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        List<TodoGroup> todoGroups = todoGroupRepository.findTop5ByJobAndMemberNot(job, member);

        return todoGroups.stream()
            .map(group -> {
                Long todoCount = todoRepository.countByTodoGroup(group);
                return GetOthersTodoSimpleResponseDto.of(group, todoCount);
            })
            .collect(Collectors.toList());

    }


    // 특정 직업에 대한 타 유저 투두 리스트 조회( 전체 )
    @Transactional(readOnly = true)
    public Page<GetOthersTodoGroupResponseDto> getOthersTodoByJob(Long jobId, int page) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        Pageable pageable = PageRequest.of(page, 10);
        Page<TodoGroup> todoGroups = todoGroupRepository.findTop10ByJobAndNotMember(
            job, member, pageable);

        Pageable top3 = PageRequest.of(0, 3);
        List<GetOthersTodoGroupResponseDto> result = todoGroups.stream()
            .map(group -> {
                List<Todo> todos = todoRepository.findTop3ByTodoGroup(group, top3);
                Long todoCount = todoRepository.countByTodoGroup(group);
                return GetOthersTodoGroupResponseDto.of(group, todos, todoCount);
            })
            .toList();

        return new PageImpl<>(result, pageable, todoGroups.getTotalElements());

    }

    // 타유저 - 세부 조회
    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneOthersTodoGroup(Long TodoGroupId) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMemberNot(TodoGroupId, member)
            .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        Map<TodoCategory, List<GetOneTodoResponseDto>> groupedMap =
            todoGroup.getTodo().stream()
                .map(GetOneTodoResponseDto::from)
                .collect(Collectors.groupingBy(
                    GetOneTodoResponseDto::todoCategory, LinkedHashMap::new, Collectors.toList()
                ));

        List<GetTodoGroupByCategoryResponseDto> todos = Arrays.stream(TodoCategory.values())
            .filter(groupedMap::containsKey)
            .map(category -> GetTodoGroupByCategoryResponseDto.of(category,
                groupedMap.get(category)))
            .collect(Collectors.toList());

        return GetOneTodoGroupResponseDto.of(member, todoGroup, todos);

    }
}
