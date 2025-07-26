package com.dodream.todo.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.dodream.core.application.ObjectStorageService;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.domain.JobTodo;
import com.dodream.job.domain.Level;
import com.dodream.job.domain.PhysicalActivity;
import com.dodream.job.domain.Require;
import com.dodream.job.domain.SalaryType;
import com.dodream.job.domain.WorkTime;
import com.dodream.job.repository.JobRepository;
import com.dodream.job.repository.JobTodoRepository;
import com.dodream.member.application.MemberAuthService;
import com.dodream.member.application.MemberService;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.repository.TodoGroupRepository;
import com.dodream.todo.repository.TodoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private JobTodoRepository jobTodoRepository;
    @Mock
    private TodoGroupRepository todoGroupRepository;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ObjectStorageService objectStorageService;
    @Mock
    private MemberService memberService;
    @Mock
    private MemberAuthService memberAuthService;
    @Mock
    private TodoMemberService todoMemberService;
    @InjectMocks
    private TodoService todoService;

    private static final Long TEST_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final State TEST_STATE = State.ACTIVE;
    private static final String TEST_REGION_CODE1 = "11110";
    private static final String TEST_REGION_NAME1 = "서울 종로구";
    private Member mockMember;
    private Member otherMember;
    private Region region1;
    private Job job1;
    private Job job2;
    private JobTodo jobTodo;
    private TodoGroup todoGroup1;
    private TodoGroup other_todoGroup;
    private Todo todo1;
    private Todo todo2;
    private Todo other_todo;


    @Nested
    @DisplayName("타유저 투두")
    class HomeTodoTest {

        @BeforeEach
        void setupSecurityContext() {

            //given
            region1 = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE1)
                .regionName(TEST_REGION_NAME1)
                .build();

            mockMember = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .state(TEST_STATE)
                .region(region1)
                .build();

            otherMember = Member.builder()
                .id(OTHER_MEMBER_ID)
                .loginId("other_member_id")
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName("other_nickname")
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .state(TEST_STATE)
                .region(region1)
                .build();

            job1 = Job.builder()
                .id(1L)
                .jobName("요양 보호사")
                .requiresCertification(Require.REQUIRED)
                .workTimeSlot(WorkTime.FLEXIBLE)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(2500000)
                .interpersonalContactLevel(Level.MEDIUM)
                .physicalActivityLevel(PhysicalActivity.HIGH)
                .emotionalLaborLevel(Level.HIGH)
                .jobImageUrl("https://example.com/image.jpg")
                .ncsName("노인복지서비스")
                .certifications(new ArrayList<>())
                .build();

            job2 = Job.builder()
                .id(2L)
                .jobName("심리 상담사")
                .requiresCertification(Require.REQUIRED)
                .workTimeSlot(WorkTime.FLEXIBLE)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(2500000)
                .interpersonalContactLevel(Level.MEDIUM)
                .physicalActivityLevel(PhysicalActivity.HIGH)
                .emotionalLaborLevel(Level.HIGH)
                .jobImageUrl("https://example.com/image.jpg")
                .ncsName("심리상담서비스")
                .certifications(new ArrayList<>())
                .build();

            jobTodo = JobTodo.builder()
                .id(1L)
                .job(job1)
                .title("test title")
                .build();

            todoGroup1 = TodoGroup.builder()
                .id(1L)
                .job(job1)
                .member(mockMember)
                .todo(new ArrayList<>())
                .totalView((0L))
                .createdAt(LocalDateTime.now())
                .build();

            other_todoGroup = TodoGroup.builder()
                .id(2L)
                .job(job1)
                .member(otherMember)
                .todo(new ArrayList<>())
                .totalView(0L)
                .createdAt(LocalDateTime.now())
                .build();

            todo1 = Todo.builder()
                .todoGroup(todoGroup1)
                .member(mockMember)
                .title("title")
                .build();

            todo2 = Todo.builder()
                .todoGroup(todoGroup1)
                .member(mockMember)
                .title("title")
                .build();

            other_todo = Todo.builder()
                .todoGroup(todoGroup1)
                .member(mockMember)
                .title("other_title")
                .build();

            CustomUserDetails userDetails = new CustomUserDetails(mockMember);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        @AfterEach
        void clearSecurityContext() {
            SecurityContextHolder.clearContext();
        }

        @Test
        @DisplayName("타유저 투두 그룹 조회")
        void getOtherOneTodoGroup() {

            // given
            when(todoGroupRepository.findTop3ByTotalView(any(Pageable.class)))
                .thenReturn(List.of(todoGroup1));
            when(todoRepository.findTop2ByTodoGroup(any(), any()))
                .thenReturn(List.of(todo1, todo2));
            when(todoRepository.countByTodoGroup(any()))
                .thenReturn(1L);

            // when
            List<GetOthersTodoGroupResponseDto> result = todoService.getOneTodoGroupPublicAtHome();

            // then
            assertEquals(1, result.size());
            for (GetOthersTodoGroupResponseDto dto : result) {
                assertEquals(2, dto.todos().size());
                assertEquals(1L, dto.todoCount());
            }

        }

        @Test
        @DisplayName("타유저 투두 메모 조회")
        void getOtherOneTodo() {

            // given
            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job1));
            when(todoGroupRepository.findTop3ByJobAndNotMember(eq(job1), eq(mockMember), any()))
                .thenReturn(List.of(other_todoGroup));
            when(todoRepository.findTop2ByTodoGroup(eq(other_todoGroup), any()))
                .thenReturn(List.of(other_todo));
            when(todoRepository.countByTodoGroup(any())).thenReturn(1L);

            // when
            List<GetOthersTodoGroupResponseDto> result = todoService.getOthersTodoSimple(1L);

            // then
            assertEquals(1, result.size());
            for (GetOthersTodoGroupResponseDto dto : result) {
                assertEquals(1, dto.todos().size());
                assertEquals(1L, dto.todoCount());
            }

        }

        @Test
        @DisplayName("특정 직업 타유저 투두 그룹 목록 조회")
        void getOneJobOtherOneTodoGroupList() {

            // given
            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job1));

            Page<TodoGroup> todoGroupPage = new PageImpl<>(List.of(todoGroup1));
            when(todoGroupRepository.findTop10ByJobAndNotMember(eq(job1), eq(mockMember), any()))
                .thenReturn(todoGroupPage);

            when(todoRepository.findTop3ByTodoGroup(any(), any()))
                .thenReturn(List.of(other_todo));

            when(todoRepository.countByTodoGroup(any())).thenReturn(1L);

            // when
            Page<GetOthersTodoGroupResponseDto> result = todoService.getOthersTodoByJob(1L, 0);

            // then
            assertEquals(1, result.getContent().size());
            for (GetOthersTodoGroupResponseDto dto : result.getContent()) {
                assertEquals(1, dto.todos().size());
                assertEquals(1L, dto.todoCount());
            }
            assertEquals(0, result.getNumber());
            assertEquals(1, result.getTotalElements());

        }

        @Test
        @DisplayName("타유저 투두 리스트 상세 조회")
        void getOtherOneTodoGroupList() {

            // given
            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(todoGroupRepository.findByIdAndMemberNot(2L, mockMember))
                .thenReturn(Optional.of(other_todoGroup));

            // when
            GetOneTodoGroupResponseDto result = todoService.getOneOthersTodoGroup(2L);

            //then
            assertEquals(other_todoGroup.getId(), result.todoGroupId());
            assertEquals(otherMember.getNickName(), result.memberNickname());
            assertEquals(other_todoGroup.getJob().getJobName(), result.jobName());

        }

    }
}