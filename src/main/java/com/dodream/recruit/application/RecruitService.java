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

    public RecruitResponseListDto getRecruitList(
            String keyWord, String locationName,
            String startDate, String endDate, int pageNum
    ){
        if(keyWord == null){
            keyWord = getTopJobNameInTodo();
        }

        String result = recruitApiCaller.recruitListApiListCaller(
                    keyWord, recruitCodeResolver.resolveRecruitLocationName(locationName),
                    getDateTimeString(startDate), getDateTimeString(endDate), pageNum
        );

        RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

        return recruitMapper.toSimpleListDto(mappedResult);
    }

    public RecruitResponseListDto getRecruitListByToken(
            CustomUserDetails customUserDetails, String keyWord, String locationName,
            String startDate, String endDate, int pageNum
    ){
        if(keyWord == null){
            keyWord = getTopJobNameInMemberTodoGroup(customUserDetails);
        }
        String result = recruitApiCaller.recruitListApiListCaller(
                keyWord,
                getRegionCodeByToken(customUserDetails),
                getDateTimeString(startDate),
                getDateTimeString(endDate),
                pageNum
        );

        RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

        return recruitMapper.toSimpleListDto(mappedResult);
    }

    public List<PopularRecruitResponse> getPopularJobListByAllMember(){
        List<Job> popularJobList = jobRepository.findTop3PopularJob();

        // 만약 아무것도 안담긴 경우 랜덤하게 3개 담기
        if(popularJobList.isEmpty() || popularJobList.size() < 3){
            popularJobList = jobRepository.findAll();
            Collections.shuffle(popularJobList);
            popularJobList = popularJobList.stream().limit(3).collect(Collectors.toList());
        }

        List<PopularRecruitResponse> popularRecruitResponseList = new ArrayList<>();
        for(Job job : popularJobList){
            String result = recruitApiCaller.recruitListApiListCaller(
                    job.getJobName(),
                    null,
                    null,
                    null,
                    0
            );

            RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

            popularRecruitResponseList.add(new PopularRecruitResponse(
                    job.getJobName(),
                    Integer.parseInt(mappedResult.jobs().total())
            ));
        }

        return popularRecruitResponseList;
    }

    public List<PopularRecruitResponse> getPopularJobListByTodoGroup(CustomUserDetails customUserDetails){
        Member member = memberRepository.findById(customUserDetails.getId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        List<Job> jobList = todoGroupRepository.findTop3ByMemberOrderByTotalViewDesc(member)
                .stream()
                .map(TodoGroup::getJob)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 만약 아무것도 안담긴 경우 랜덤하게 3개 담기
        if(jobList.isEmpty() || jobList.size() < 3){
            jobList = jobRepository.findAll();
            Collections.shuffle(jobList);
            jobList = jobList.stream().limit(3).collect(Collectors.toList());
        }

        List<PopularRecruitResponse> popularRecruitResponseList = new ArrayList<>();

        for (Job job : jobList){
            String result = recruitApiCaller.recruitListApiListCaller(
                    job.getJobName(),
                    getRegionCodeByToken(customUserDetails),
                    null,
                    null,
                    0
            );

            RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

            popularRecruitResponseList.add(new PopularRecruitResponse(
                    job.getJobName(),
                    Integer.parseInt(mappedResult.jobs().total())
            ));
        }

        return popularRecruitResponseList;
    }

    public RecruitResponseListDto getRecruitDetail(
            String id
    ) {
        String result = recruitApiCaller.recruitDetailAPiCaller(id);

        RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

        return recruitMapper.toSimpleListDto(mappedResult);
    }

    private String getDateTimeString(String date){
        if(date == null || date.isEmpty()){
            return null;
        }

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate localDate = LocalDate.parse(date, inputDateFormatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();

        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul"); // KST (UTC+9)
        long unixTimestampSecondsSeoul = localDateTime.atZone(seoulZoneId).toEpochSecond();
        return String.valueOf(unixTimestampSecondsSeoul);
    }

    private String getRegionCodeByToken(CustomUserDetails customUserDetails) {
        Member member = memberRepository.findById(customUserDetails.getId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if(member.getRegion() == null) {
            return null;
        } else{
            return member.getRegion().getSaraminRegionCode();
        }
    }

    private String getTopJobNameInMemberTodoGroup(CustomUserDetails customUserDetails){
        Member member = memberRepository.findById(customUserDetails.getId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        TodoGroup todoGroup = todoGroupRepository.findTopByMemberOrderByTotalViewDesc(member)
                .orElseThrow(TodoGroupErrorCode.TODO_GROUP_NOT_FOUND::toException);

        Job job = todoGroup.getJob();

        return job.getJobName();
    }

    private String getTopJobNameInTodo(){
        Job job = jobRepository.findMostPopularJob()
                .orElseThrow(JobErrorCode.CANNOT_GET_JOB_DATA::toException);

        return job.getJobName();
    }
}
