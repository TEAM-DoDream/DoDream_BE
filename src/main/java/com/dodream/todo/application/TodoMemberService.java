package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.TodoCategory;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobTodoRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoGroupResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoGroupByCategoryResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoMemberService {

    private final MemberAuthService memberAuthService;
    private final JobTodoRepository jobTodoRepository;
    private final JobRepository jobRepository;
    private final TodoRepository todoRepository;
    private final TodoGroupRepository todoGroupRepository;

    @Transactional
    public PostTodoResponseDto postNewTodo(Long todoGroupId, PostTodoRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMember(todoGroupId,
                member)
            .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        Todo newTodo = Todo.of(todoGroup, member, requestDto.todoTitle(),
            requestDto.todoCategory());
        todoRepository.save(newTodo);

        return PostTodoResponseDto.of(todoGroup.getId(), newTodo);
    }


    @Transactional
    public AddJobTodoResponseDto addJobToMyList(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        if (todoGroupRepository.existsByMemberAndJob(member, job)) {
            throw TodoGroupErrorCode.JOB_EXISTS.toException();
        }

        List<JobTodo> todoList = jobTodoRepository.findAllByJob(job);

        TodoGroup todoGroup = TodoGroup.builder()
            .member(member)
            .job(job)
            .build();

        TodoGroup savedGroup = todoGroupRepository.save(todoGroup);

        List<Todo> myTodos = todoList.stream()
            .map(todo -> Todo.of(savedGroup, member, todo))
            .toList();

        todoRepository.saveAll(myTodos);

        return AddJobTodoResponseDto.of(member, todoGroup);
    }


    @Transactional(readOnly = true)
    public List<GetTodoJobResponseDto> getTodoJobList() {

        Member member = memberAuthService.getCurrentMember();

        List<TodoGroup> todoGroups = todoGroupRepository.findAllByMember(member);

        return todoGroups.stream()
            .map(GetTodoJobResponseDto::from)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneTodoGroupAtHome() {

        Member member = memberAuthService.getCurrentMember();

        Optional<TodoGroup> todoGroup = todoGroupRepository.findFirstByMemberOrderByIdAsc(member);

        if (todoGroup.isEmpty()) {
            return GetOneTodoGroupResponseDto.empty(member);
        }

        Map<TodoCategory, List<GetOneTodoResponseDto>> groupedMap = todoGroup.get().getTodo()
            .stream()
            .map(GetOneTodoResponseDto::from)
            .collect(Collectors.groupingBy(
                GetOneTodoResponseDto::todoCategory,
                LinkedHashMap::new,
                Collectors.toList()
            ));

        List<GetTodoGroupByCategoryResponseDto> todos = Arrays.stream(TodoCategory.values())
            .filter(groupedMap::containsKey)
            .map(category -> GetTodoGroupByCategoryResponseDto.of(
                category, groupedMap.get(category)))
            .toList();

        return GetOneTodoGroupResponseDto.of(member, todoGroup.get(), todos);
    }


    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneTodoGroup(Long TodoGroupId) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMember(TodoGroupId, member)
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

    @Transactional(readOnly = true)
    public GetOneTodoWithMemoResponseDto getOneTodoWithMemo(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        return GetOneTodoWithMemoResponseDto.from(todo);
    }

    @Transactional
    public DeleteTodoResponseDto deleteOneTodo(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        todoRepository.delete(todo);

        return DeleteTodoResponseDto.from(todo);
    }


    @Transactional
    public ChangePublicStateTodoResponseDto changeOneTodoPublicState(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);
        todo.updateIsPublic();

        return ChangePublicStateTodoResponseDto.from(todo);
    }

    @Transactional
    public ChangeCompleteStateTodoResponseDto changeOneTodoCompleteState(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);
        todo.updateCompleted();

        return ChangeCompleteStateTodoResponseDto.from(todo);
    }

    @Transactional
    public DeleteTodoGroupResponseDto deleteTodoGroups(List<Long> jobIds,Member member) {

        todoGroupRepository.deleteByMemberAndJobIdIn(member,jobIds);

        return DeleteTodoGroupResponseDto.from(jobIds);
    }
}
