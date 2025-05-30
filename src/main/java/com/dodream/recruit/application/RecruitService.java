package com.dodream.recruit.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.recruit.dto.response.PopularRecruitResponse;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.recruit.util.RecruitCodeResolver;
import com.dodream.recruit.util.RecruitDateUtil;
import com.dodream.todo.domain.TodoGroup;
import com.dodream.todo.exception.TodoGroupErrorCode;
import com.dodream.todo.repository.TodoGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecruitService {

    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;
    private final RecruitCodeResolver recruitCodeResolver;
    private final MemberRepository memberRepository;
    private final TodoGroupRepository todoGroupRepository;
    private final JobRepository jobRepository;

    private static final String DEFAULT_JOB = "요양보호사";

    public RecruitResponseListDto getRecruitList(
            String keyWord, String locationName, int pageNum, String sortBy
    ) {
        if (keyWord == null) {
            keyWord = getTopJobNameFromAllUserTodoGroup();
        }

        String regionCode = recruitCodeResolver.resolveRecruitLocationName(locationName);
        String result = callRecruitListApi(keyWord, regionCode, pageNum, sortBy);

        return toRecruitListDto(result, keyWord);
    }

    public RecruitResponseListDto getRecruitListByToken(
            CustomUserDetails customUserDetails, String keyWord, int pageNum, String sortBy
    ) {
        if (keyWord == null) {
            keyWord = getTopJobNameFromMemberTodoGroup(customUserDetails);
        }

        String regionCode = getRegionCodeFromMember(customUserDetails);
        String result = callRecruitListApi(keyWord, regionCode, pageNum, sortBy);

        return toRecruitListDto(result, keyWord);
    }

    public List<PopularRecruitResponse> getPopularJobListByAllMember() {
        List<Job> jobList = getTopJobListWithFallback(jobRepository.findTop3PopularJob());

        return jobList.stream()
                .map(job -> new PopularRecruitResponse(
                        job.getJobName(),
                        getRecruitCount(job.getJobName(), null)
                ))
                .collect(Collectors.toList());
    }

    public List<PopularRecruitResponse> getPopularJobListByTodoGroup(CustomUserDetails userDetails) {
        Member member = getMember(userDetails.getId());

        List<Job> jobList = todoGroupRepository.findTop3ByMemberOrderByTotalViewDesc(member).stream()
                .map(TodoGroup::getJob)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        jobList = getTopJobListWithFallback(jobList);

        String regionCode = getRegionCodeFromMember(userDetails);

        return jobList.stream()
                .map(job -> new PopularRecruitResponse(
                        job.getJobName(),
                        getRecruitCount(job.getJobName(), regionCode)
                ))
                .collect(Collectors.toList());
    }

    public RecruitResponseListDto getRecruitDetail(String id) {
        String result = recruitApiCaller.recruitDetailAPiCaller(id);
        return toRecruitListDto(result, null);
    }

    // === 헬퍼 메서드 ===

    private String callRecruitListApi(
            String keyWord, String regionCode, int pageNum, String sortBy
    ) {
        return recruitApiCaller.recruitListApiListCaller(
                keyWord,
                regionCode,
                pageNum,
                sortBy
        );
    }

    private RecruitResponseListDto toRecruitListDto(String apiResult, String keyword) {
        RecruitResponseListApiDto mapped = recruitMapper.recruitListMapper(apiResult);
        return recruitMapper.toSimpleListDto(mapped, keyword);
    }

    private String getRegionCodeFromMember(CustomUserDetails userDetails) {
        return getMember(userDetails.getId())
                .getRegion().getSaraminRegionCode();
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);
    }

    private String getTopJobNameFromMemberTodoGroup(CustomUserDetails userDetails) {
        Member member = getMember(userDetails.getId());
        return todoGroupRepository.findTopByMemberOrderByTotalViewDesc(member)
                .map(TodoGroup::getJob)
                .map(Job::getJobName)
                .orElseGet(this::getTopJobNameFromAllUserTodoGroup);
    }

    private String getTopJobNameFromAllUserTodoGroup() {
        return jobRepository.findMostPopularJob()
                .map(Job::getJobName)
                .orElse(DEFAULT_JOB);
    }

    private int getRecruitCount(String jobName, String regionCode) {
        String result = recruitApiCaller.recruitListApiListCaller(
                jobName, regionCode, 0, null
        );
        return Integer.parseInt(recruitMapper.recruitListMapper(result).jobs().total());
    }

    private List<Job> getTopJobListWithFallback(List<Job> topJobs) {
        if (topJobs == null) topJobs = new ArrayList<>();
        List<Job> result = new ArrayList<>(topJobs);

        if (result.size() < 3) {
            List<Job> allJobs = new ArrayList<>(jobRepository.findAll());
            allJobs.removeAll(result);
            Collections.shuffle(allJobs);
            result.addAll(allJobs.stream().limit(3 - result.size()).toList());
        }

        return result.stream().limit(3).collect(Collectors.toList());
    }
}
