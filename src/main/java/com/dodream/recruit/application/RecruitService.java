package com.dodream.recruit.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.job.domain.Job;
import com.dodream.job.exception.JobErrorCode;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
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
