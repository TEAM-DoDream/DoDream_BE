package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobTodoRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.AddTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteMemberJobResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.OtherTodoSaveResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
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

    // 직업 담기
    @Transactional
    public AddJobTodoResponseDto addJobToMyList(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        TodoGroup todoGroupToDelete = todoGroupRepository.findByMember(member);
        if (todoGroupToDelete != null) {
            todoGroupRepository.delete(todoGroupToDelete);
            job.minusTodoGroupNum();
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
        job.plusTodoGroupNum();

        return AddJobTodoResponseDto.of(member, todoGroup);
    }


    // 홈화면 투두 하나 조회
    @Transactional(readOnly = true)
    public GetOneTodoGroupAtHomeResponseDto getOneTodoGroupAtHome() {

        Member member = memberAuthService.getCurrentMember();

        Optional<TodoGroup> todoGroup = todoGroupRepository.findFirstByMemberOrderByIdAsc(member);

        if (todoGroup.isEmpty()) {
            return GetOneTodoGroupAtHomeResponseDto.empty(member);
        }

        List<GetOneTodoAtHomeResponseDto> todos = todoGroup.get().getTodo().stream()
            .limit(4)
            .map(GetOneTodoAtHomeResponseDto::from)
            .toList();

        return GetOneTodoGroupAtHomeResponseDto.of(member, todoGroup.get(), todos);

    }

    // 마이드림 - 투두 리스트 조회
    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneTodoGroupAtMyDream() {

        Member member = memberAuthService.getCurrentMember();

        Optional<TodoGroup> todoGroup = todoGroupRepository.findFirstByMemberOrderByIdAsc(member);

        if (todoGroup.isEmpty()) {
            return GetOneTodoGroupResponseDto.empty(member);
        }

        List<GetOneTodoResponseDto> todos = todoGroup.get().getTodo().stream()
            .map(todo -> GetOneTodoResponseDto.from(todo, false))
            .toList();

        return GetOneTodoGroupResponseDto.of(member, todoGroup.get(), todos);

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
    public AddTodoResponseDto addOneTodo(Long JobTodoId) {

        Member member = memberAuthService.getCurrentMember();

        JobTodo jobTodo = jobTodoRepository.findById(JobTodoId)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        TodoGroup todoGroup = todoGroupRepository.findByMember(member);
        if (todoGroup == null){
            throw TodoGroupErrorCode.JOB_NOT_ADDED.toException();
        }

        Todo newTodo = Todo.of(todoGroup,member,jobTodo.getTitle());
        todoRepository.save(newTodo);

        return AddTodoResponseDto.of(member,jobTodo);
    }

    @Transactional
    public ChangeCompleteStateTodoResponseDto changeOneTodoCompleteState(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);
        todo.updateCompleted();

        return ChangeCompleteStateTodoResponseDto.from(todo);
    }

    // 새로운 투두 아이템 생성
    @Transactional
    public PostTodoResponseDto postNewTodo(PostTodoRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();
        TodoGroup todoGroup = todoGroupRepository.findByMember(member);

        if (todoGroup == null) {
            throw TodoGroupErrorCode.JOB_NOT_ADDED.toException();
        }

        Todo newTodo = Todo.of(todoGroup, member, requestDto.todoTitle());
        todoRepository.save(newTodo);

        return PostTodoResponseDto.of(todoGroup.getId(), newTodo);
    }

    // 투두 아이템 수정
    @Transactional
    public ModifyTodoResponseDto modifyTodo(Long todoId, ModifyTodoRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        todo.updateTitle(requestDto.todoTitle());
        todoRepository.save(todo);

        return ModifyTodoResponseDto.of(todo.getTodoGroup().getId(), todo);
    }

    @Transactional
    public DeleteMemberJobResponseDto deleteMemberJob() {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroupToDelete = todoGroupRepository.findByMember(member);

        if (todoGroupToDelete == null) {
            throw TodoGroupErrorCode.JOB_NOT_ADDED.toException();
        }

        Job job = todoGroupToDelete.getJob();
        todoGroupRepository.delete(todoGroupToDelete);
        job.minusTodoGroupNum();

        return DeleteMemberJobResponseDto.from(member);
    }

}
