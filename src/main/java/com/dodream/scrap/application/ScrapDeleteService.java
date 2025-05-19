package com.dodream.scrap.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.dto.response.ScrapDeletedResponse;
import com.dodream.scrap.exception.ScrapErrorCode;
import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.MemberTrainingScrapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapDeleteService {

    private final MemberTrainingScrapRepository memberTrainingScrapRepository;
    private final MemberRecruitScrapRepository memberRecruitScrapRepository;

    @Transactional
    public ScrapDeletedResponse deleteRecruitScrap(
            String recruitId, CustomUserDetails customUserDetails
    ){
        Long memberId = getMemberId(customUserDetails);

        MemberRecruitScrap memberRecruitScrap = memberRecruitScrapRepository.findByRecruitIdAndMemberId(recruitId, memberId)
                .orElseThrow(ScrapErrorCode.NOT_FOUND_SCRAP::toException);

        memberRecruitScrapRepository.delete(memberRecruitScrap);

        return ScrapDeletedResponse.of(memberRecruitScrap.getId());
    }

    @Transactional
    public ScrapDeletedResponse deletedTrainingScrap(
            String trainingId, CustomUserDetails customUserDetails
    ){
        Long memberId = getMemberId(customUserDetails);

        MemberTrainingScrap memberTrainingScrap = memberTrainingScrapRepository.findByTrainingIdAndMemberId(trainingId, memberId)
                .orElseThrow(ScrapErrorCode.NOT_FOUND_SCRAP::toException);

        memberTrainingScrapRepository.delete(memberTrainingScrap);

        return ScrapDeletedResponse.of(memberTrainingScrap.getId());
    }

    private Long getMemberId(CustomUserDetails customUserDetails) {
        if(customUserDetails == null) throw MemberErrorCode.MEMBER_NOT_FOUND.toException();
        return customUserDetails.getId();
    }
}
