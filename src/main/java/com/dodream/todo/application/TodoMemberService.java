package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.TodoCategory;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.ncs.repository.JobTodoRespository;
import com.dodream.todo.domain.MemoImage;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoGroupByCategoryResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
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
    private final JobTodoRespository jobTodoRespository;
    private final JobRepository jobRepository;
    private final TodoRepository todoRepository;
    private final TodoGroupRepository todoGroupRepository;

    // 직업 담기
    @Transactional
    public AddJobTodoResponseDto addJobToMyList(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        List<JobTodo> todoList = jobTodoRespository.findAllByJob(job);  // 해당 직업의 투두 가져오기

        TodoGroup todoGroup = TodoGroup.builder()  //새로운 MyJobTodoGroup 생성
            .member(member)
            .job(job)
            .build();

        TodoGroup savedGroup = todoGroupRepository.save(todoGroup);

        List<Todo> myTodos = todoList.stream()   // 투두 생성하기
            .map(todo -> Todo.of(savedGroup, member, todo))
            .toList();

        todoRepository.saveAll(myTodos);

        return AddJobTodoResponseDto.of(member, todoGroup);
    }


    // 자신이 담은 직업 목록 조회
    @Transactional(readOnly = true)
    public List<GetTodoJobResponseDto> getTodoJobList() {
        Member member = memberAuthService.getCurrentMember();

        List<TodoGroup> todoGroups = todoGroupRepository.findAllByMember(member);

        return todoGroups.stream()
            .map(GetTodoJobResponseDto::from)
            .collect(Collectors.toList());

    }

    // 투두 처음 화면 조회
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
            .map(category -> new GetTodoGroupByCategoryResponseDto(
                category.getValue(), groupedMap.get(category)))
            .toList();

        return GetOneTodoGroupResponseDto.of(member, todoGroup.get(), todos);
    }


    // 개별 투두 그룹 조회
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
            .map(category -> new GetTodoGroupByCategoryResponseDto(category.getValue(),
                groupedMap.get(category)))
            .collect(Collectors.toList());

        return GetOneTodoGroupResponseDto.of(member, todoGroup, todos);

    }

    // 투두 메모 조회
    @Transactional(readOnly = true)
    public GetOneTodoWithMemoResponseDto getOneTodoWithMemo(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMemberAndDeletedIsFalse(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_FOUND_NCS::toException);

        return GetOneTodoWithMemoResponseDto.from(todo);
    }

    // 투두 삭제
    @Transactional
    public DeleteTodoResponseDto deleteOneTodo(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMemberAndDeletedIsFalse(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_FOUND_NCS::toException);
        todo.updateDeleted();

        todo.getImages()
            .forEach(MemoImage::updateDeleted);

        return DeleteTodoResponseDto.from(todo);
    }

    // 투두 메모 공개 여부 결정
    @Transactional
    public ChangePublicStateTodoResponseDto changeOneTodoPublicState(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMemberAndDeletedIsFalse(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_FOUND_NCS::toException);
        todo.updateIsPublic();

        return ChangePublicStateTodoResponseDto.from(todo);

    }

    // 투두 완료 처리
    @Transactional
    public ChangeCompleteStateTodoResponseDto changeOneTodoCompleteState(Long todoId) {

        Member member = memberAuthService.getCurrentMember();

        Todo todo = todoRepository.findByIdAndMemberAndDeletedIsFalse(todoId, member)
            .orElseThrow(TodoErrorCode.TODO_FOUND_NCS::toException);
        todo.updateCompleted();

        return ChangeCompleteStateTodoResponseDto.from(todo);

    }

    // 투두 메모 작성하기

    // 특정 투두 수정하기

    // 새로운 투두 생성하기

    // 이미지 첨부하기


}
