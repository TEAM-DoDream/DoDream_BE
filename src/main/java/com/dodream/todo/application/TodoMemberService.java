package com.dodream.todo.application;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobTodoRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.domain.TodoImage;
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoGroupResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoImageRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
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
    private final TodoImageRepository todoImageRepository;
    private final ObjectStorageService objectStorageService;

    // 직업 담기
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
        job.plusTodoGroupNum();

        return AddJobTodoResponseDto.of(member, todoGroup);
    }


    // 담은 직업 목록 조회
    @Transactional(readOnly = true)
    public List<GetTodoJobResponseDto> getTodoJobList() {

        Member member = memberAuthService.getCurrentMember();

        List<TodoGroup> todoGroups = todoGroupRepository.findAllByMember(member);

        return todoGroups.stream()
            .map(GetTodoJobResponseDto::from)
            .collect(Collectors.toList());
    }


    // 홈화면 todo 하나 조회
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
            .map(GetOneTodoResponseDto::from)
            .toList();

        return GetOneTodoGroupResponseDto.of(member, todoGroup.get(), todos);

    }


    // 개별 투두 리스트 조회
    @Transactional(readOnly = true)
    public GetOneTodoGroupResponseDto getOneTodoGroup(Long TodoGroupId) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMember(TodoGroupId, member)
            .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        List<GetOneTodoResponseDto> todos = todoGroup.getTodo().stream()
            .map(GetOneTodoResponseDto::from)
            .toList();

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
    public DeleteTodoGroupResponseDto deleteTodoGroups(List<Long> jobIds, Member member) {

        List<TodoGroup> todoGroupsToDelete = todoGroupRepository.findByMemberAndJobIdIn(member,
            jobIds);

        todoGroupRepository.deleteAll(todoGroupsToDelete);

        for (Long jobId : jobIds) {
            Job job = jobRepository.findById(jobId)
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);
            job.minusTodoGroupNum();
        }

        return DeleteTodoGroupResponseDto.from(jobIds);
    }


    // 새로운 투두 아이템 생성
    @Transactional
    public PostTodoResponseDto postNewTodo(Long todoGroupId, PostTodoRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        TodoGroup todoGroup = todoGroupRepository.findByIdAndMember(todoGroupId,
                member)
            .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        Todo newTodo = Todo.of(todoGroup, member, requestDto.todoTitle(), requestDto.memoText(),
            requestDto.link(), requestDto.isPublic());
        todoRepository.save(newTodo);

        List<String> createdImageUrls = new ArrayList<>();
        if (requestDto.images() != null && !requestDto.images().isEmpty()) {
            createdImageUrls = objectStorageService.uploadTodoMemoImages(
                requestDto.images(),
                newTodo.getId());

            List<TodoImage> newImages = createdImageUrls.stream()
                .map(image -> TodoImage.of(newTodo, image))
                .toList();

            todoImageRepository.saveAll(newImages);
        }

        return PostTodoResponseDto.of(todoGroup.getId(), newTodo, createdImageUrls);
    }

    // 투두 아이템 수정
    @Transactional
    public ModifyTodoResponseDto modifyTodo(Long todoId, ModifyTodoRequestDto requestDto) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMember(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        todo.updateTitle(requestDto.todoTitle());
        todo.updateMemoText(requestDto.memoText());
        todo.updateLink(requestDto.link());
        todo.initialIsPublic(requestDto.isPublic());

        List<Long> deleteImageIds = requestDto.deleteImages();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            List<TodoImage> imagesToDelete = todoImageRepository.findAllById(deleteImageIds);
            todoImageRepository.deleteAll(imagesToDelete);
        }

        List<String> newImageUrls = new ArrayList<>();
        if (requestDto.newImages() != null && !requestDto.newImages().isEmpty()) {
            newImageUrls = objectStorageService.uploadTodoMemoImages(
                requestDto.newImages(),
                todo.getId());

            List<TodoImage> newImages = newImageUrls.stream()
                .map(image -> TodoImage.of(todo, image))
                .toList();

            todoImageRepository.saveAll(newImages);
        }

        todoRepository.save(todo);

        return ModifyTodoResponseDto.of(todo.getTodoGroup().getId(), todo);
    }


}
