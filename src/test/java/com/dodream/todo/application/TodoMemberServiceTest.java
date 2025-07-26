package com.dodream.todo.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class TodoMemberServiceTest {

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
    @InjectMocks
    private TodoMemberService todoMemberService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final State TEST_STATE = State.ACTIVE;
    private static final String TEST_REGION_CODE1 = "11110";
    private static final String TEST_REGION_NAME1 = "서울 종로구";
    private Member mockMember;
    private Region region1;
    private Job job1;
    private Job job2;
    private JobTodo jobTodo;
    private TodoGroup todoGroup1;
    private TodoGroup todoGroup2;
    private Todo todo1;
    private Todo todo2;


    @Nested
    @DisplayName("마이드림")
    class HomeTodoTest {

        @BeforeEach
        void setupSecurityContext() {

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

            todoGroup2 = TodoGroup.builder()
                .id(2L)
                .job(job2)
                .member(mockMember)
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
        @DisplayName("직업 담기")
        void addNewJob() {

            // given
            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job1));
            when(todoGroupRepository.save(any(TodoGroup.class))).thenReturn(todoGroup1);

            // when
            AddJobTodoResponseDto responseDto = todoMemberService.addJobToMyList(1L);

            //then
            assertAll(
                () -> assertThat(todoGroup1).isNotNull(),
                () -> assertThat(todoGroup1.getMember().getId()).isEqualTo(responseDto.memberId()),
                () -> assertThat("직업 담기 완료").isEqualTo(responseDto.message())
            );
        }



        @Test
        @DisplayName("메모 작성")
        void postTodoMemo() {

            // given
            PostTodoRequestDto requestDto = new PostTodoRequestDto("title");

            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(todoRepository.save(any(Todo.class))).thenReturn(todo1);

            when(todoGroupRepository.findByIdAndMember(1L, mockMember))
                .thenReturn(Optional.of(todoGroup1));

            // when
            PostTodoResponseDto result = todoMemberService.postNewTodo(1L, requestDto);

            //then
            assertEquals(todoGroup1.getId(), result.todoGroupId());
            assertEquals(requestDto.todoTitle(), result.todoTitle());
        }

        @Test
        @DisplayName("투두 수정")
        void modifyTodoMemo() {

            // given
            ModifyTodoRequestDto requestDto = new ModifyTodoRequestDto("modifiedTitle");

            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(todoRepository.save(any(Todo.class))).thenReturn(todo1);
            when(todoRepository.findByIdAndMember(1L, mockMember))
                .thenReturn(Optional.of(todo1));

            // when
            ModifyTodoResponseDto result = todoMemberService.modifyTodo(1L, requestDto);

            //then
            assertEquals(todoGroup1.getId(), result.todoGroupId());
            assertEquals(requestDto.todoTitle(), result.todoTitle());
            assertEquals("투두가 수정되었습니다.", result.message());

        }


        @Test
        @DisplayName("투두 완료 상태 변경")
        void modifyTodoIsCompleteState() {

            // given
            when(memberAuthService.getCurrentMember()).thenReturn(mockMember);
            when(todoRepository.findByIdAndMember(1L, mockMember))
                .thenReturn(Optional.of(todo1));

            // when
            ChangeCompleteStateTodoResponseDto result = todoMemberService.changeOneTodoCompleteState(
                1L);

            //then
            assertEquals(true, result.completed());
            assertEquals(todo1.getId(), result.todoId());
            assertEquals("투두의 완료 상태가 변경되었습니다.", result.message());

        }
    }
}