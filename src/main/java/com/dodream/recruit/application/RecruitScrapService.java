package com.dodream.recruit.application;

import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.recruit.domain.MemberRecruitScrap;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.scrap.RecruitSavedResponseDto;
import com.dodream.recruit.exception.RecruitErrorCode;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.recruit.repository.MemberRecruitScrapRepository;
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

        if(memberRecruitScrapRepository.existsByRecruitIdAndMemberId(recruitId, member.getId())){
            throw RecruitErrorCode.POST_IS_SAVED.toException();
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
