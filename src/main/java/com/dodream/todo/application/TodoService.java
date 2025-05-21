package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    // 홈화면에서 타유저의 투두 리스트 간편 조회(3개씩)
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


    // 특정 직업 정보 페이지 - 우측 상단에 타유저 리스트
    @Transactional(readOnly = true)
    public List<GetOthersTodoGroupResponseDto> getOthersTodoSimple(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        Pageable limit3 = PageRequest.of(0, 2);
        List<TodoGroup> todoGroups = todoGroupRepository.findTop3ByJobAndNotMember(
            job, member, limit3);

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


    // 타유저의 투두 리스트 상세 조회
    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneOthersTodoGroup(Long TodoGroupId) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMemberNot(TodoGroupId, member)
            .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        List<GetOneTodoResponseDto> todos = todoGroup.getTodo().stream()
            .map(GetOneTodoResponseDto::from)
            .toList();

        return GetOneTodoGroupResponseDto.of(member, todoGroup, todos);
    }

    // 타유저 투두 리스트 메모 조회
    @Transactional(readOnly = true)
    public GetOneTodoWithMemoResponseDto getOneOthersTodoMemo(Long TodoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMemberNot(TodoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        if (!todo.getIsPublic()){
            throw TodoErrorCode.TODO_MEMO_NOT_PUBLIC.toException();
        }

        return GetOneTodoWithMemoResponseDto.from(todo);
    }
}
