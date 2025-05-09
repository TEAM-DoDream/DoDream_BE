package com.dodream.todo.application;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.domain.Member;
import com.dodream.ncs.repository.JobTodoRespository;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTodoService {

    private final MemberAuthService memberAuthService;
    private final JobTodoRespository jobTodoRespository;
    private final JobRepository jobRepository;
    private final TodoRepository todoRepository;
    private final TodoGroupRepository todoGroupRepository;


    public AddJobTodoResponseDto addJobToMyList(Long jobId) {

        Member member = memberAuthService.getCurrentMember();

        Job job = jobRepository.findById(jobId)
            .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        // 해당 직업의 투두 가져오기
        List<JobTodo> todoList = jobTodoRespository.findAllByJob(job);

        //새로운 MyJobTodoGroup 생성
        TodoGroup todoGroup = TodoGroup.builder()
            .member(member)
            .job(job)
            .build();

        // 투두 생성하기
        List<Todo> myTodos = todoList.stream()
            .map(todo -> Todo.of(todoGroup, member, todo))
            .toList();

        todoGroupRepository.save(todoGroup);

        return AddJobTodoResponseDto.of(member, todoGroup);
    }

    // 특정 투두 수정하기
    // 새로운 투두 생성하기
    // 기존 투두 삭제하기
    // 특정 투두 메모 작성하기
    // 이미지 첨부하기
    // 특정 투두 메모 공개 여부 설정하기

}
