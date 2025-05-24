package com.dodream.scrap.application.save;

import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.scrap.dto.response.RecruitSavedResponseDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.scrap.exception.ScrapErrorCode;
import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitScrapService {

    private final MemberRecruitScrapRepository memberRecruitScrapRepository;
    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;
    private final MemberRepository memberRepository;

    @Transactional
    public RecruitSavedResponseDto saveRecruit(String recruitId){
        Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if(memberRecruitScrapRepository.countByMemberId(member.getId()) >= 50){
            throw ScrapErrorCode.SCRAP_LIMIT_EXCEEDED.toException();
        }

        if(memberRecruitScrapRepository.existsByRecruitIdAndMemberId(recruitId, member.getId())){
            throw ScrapErrorCode.POST_IS_SAVED.toException();
        }

        RecruitResponseListApiDto.Jobs.Job recruitDetail = recruitMapper.parse(
                        recruitApiCaller.recruitDetailAPiCaller(recruitId),
                        RecruitResponseListApiDto.class
                ).jobs().job().getFirst();

        MemberRecruitScrap memberRecruitScrap = MemberRecruitScrap.of(recruitId, recruitDetail, member);

        memberRecruitScrapRepository.save(memberRecruitScrap);

        return new RecruitSavedResponseDto(
                member.getId(),
                true
        );
    }
}
