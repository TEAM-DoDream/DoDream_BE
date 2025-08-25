package com.dodream.todo.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Level;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.OtherTodoSaveResponseDto;
import com.dodream.todo.dto.response.TodoCommunityResponse;
import com.dodream.todo.dto.response.TodoCommunityResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoCommunityService {

    private final TodoRepository todoRepository;
    private final TodoGroupRepository todoGroupRepository;
    private final MemberRepository memberRepository;

    // 초기 렌더링시 필터의 이름을 정한다.
    public String getJobFilterName(CustomUserDetails customUserDetails) {
        if(customUserDetails == null){
            // 비로그인인 경우 할일 데이터가 가장 많은 직업 이름
            return todoRepository.findJobWithMostTodos().getJobName();
        }else {
            // 로그인한 경우 유저가 입력한 직업 이름
            return todoGroupRepository.findByMember(getMember(customUserDetails)).getJob().getJobName();
        }
    }

    // 다른 사람의 투두를 저장한다.
    @Transactional
    public OtherTodoSaveResponseDto saveOtherTodo(CustomUserDetails customUserDetails, Long otherTodoId) {
        Member member = getMember(customUserDetails);
        TodoGroup todoGroup = todoGroupRepository.findByMember(member);

        Todo otherTodo = todoRepository.findById(otherTodoId)
                .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        // 이미 저장된 투두의 경우 저장 x
        if(todoRepository.existsByMemberAndOtherTodoId(member, otherTodoId)){
            throw TodoErrorCode.IS_SAVED_IN_MY_TODO.toException();
        }

        // 내 투두도 저장 x
        if(otherTodo.getMember().getId().equals(member.getId())){
            throw TodoErrorCode.IS_MY_TODO.toException();
        }

        // 1. 다른 사람 투두를 저장한다.
        Todo myTodo = new Todo(todoGroup, member, otherTodo.getTitle(), 0L,false, otherTodoId);
        todoRepository.save(myTodo);

        // 2. 다른 사람 투두의 저장 횟수를 1 늘린다
        otherTodo.increaseSaveCount();

        // 3. return
        return new OtherTodoSaveResponseDto(
                myTodo.getId(),
                "저장에 성공했습니다."
        );
    }

    // 다른 사람의 투두 저장을 취소한다.
    @Transactional
    public Boolean cancelSaveOtherTodo(CustomUserDetails customUserDetails, Long id) {
        // 1. 멤버 여부 확인
        Member member = getMember(customUserDetails);

        // 2. 본인의 투두에 해당 투두가 있는지 확인
        Todo myTodo = todoRepository.findByOtherTodoIdAndMember(id, member)
                .orElseThrow(TodoErrorCode.TODO_NOT_FOUND::toException);

        // 3. 원본 투두의 ID를 myTodo에서 가져온다
        Long otherTodoId = myTodo.getOtherTodoId();

        // 4. 본인의 투두 삭제
        todoRepository.delete(myTodo);

        // 5. 원본 투두를 찾아 저장 횟수 1 감소
        Todo otherTodo = todoRepository.findById(otherTodoId)
                .orElseThrow(TodoErrorCode.OTHER_TODO_NOT_FOUND::toException);
        otherTodo.decreaseSaveCount();

        return true;
    }


    // 현재 직업의 투두 중 저장횟수가 가장 높은 투두를 5개 출력한다.
    public List<TodoCommunityResponseDto> findTop5TodosBySaveCount (String jobName){
        List<TodoCommunityResponse> list = todoRepository.findByTop5SaveTodo(jobName);

        return list.stream()
                .map(TodoCommunityResponseDto::of)
                .toList();
    }

    // 투두 필터에 따른 투두 목록을 출력한다(무한 스크롤)
    public Slice<TodoCommunityResponseDto> searchTodoList(
            Long memberId, String jobName, String level, String sort, int page, int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Slice<TodoCommunityResponse> slice = todoRepository.findTodosWithSlice(memberId, jobName, getLevel(level), sort, pageable);

        List<TodoCommunityResponseDto> content = slice.stream()
                .map(TodoCommunityResponseDto::of)
                .toList();

        return new SliceImpl<>(content, pageable, slice.hasNext());
    }

    private Level getLevel(String level) {
        return switch (level) {
            case "1단계: 씨앗" -> Level.SEED;
            case "2단계: 새싹" -> Level.SPROUT;
            case "3단계: 꿈나무" -> Level.TREE;
            default -> null;
        };
    }

    private Member getMember(CustomUserDetails customUserDetails) {
        if(customUserDetails == null)
            throw MemberErrorCode.MEMBER_NOT_FOUND.toException();

        Long userId = customUserDetails.getId();

        return memberRepository.findById(userId).orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);
    }
}
