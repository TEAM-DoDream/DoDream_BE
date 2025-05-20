package com.dodream.scrap.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.SortBy;
import com.dodream.scrap.dto.response.RecruitScrapResponseDto;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.MemberRecruitScrapSpecification;
import com.dodream.scrap.repository.MemberTrainingScrapRepository;
import com.dodream.scrap.repository.MemberTrainingScrapSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapSearchService {

    private final MemberRecruitScrapRepository memberRecruitScrapRepository;
    private final MemberTrainingScrapRepository memberTrainingScrapRepository;

    private final int PAGE_SIZE = 6;

    public Page<RecruitScrapResponseDto> getRecruitScrapList(
            CustomUserDetails customUserDetails, int pageNum, String locName, String sortBy
    ){

        Specification<MemberRecruitScrap> spec
                = MemberRecruitScrapSpecification.matchesFilter(customUserDetails.getId(), locName);

        Page<MemberRecruitScrap> results
                = memberRecruitScrapRepository.findAll(spec, getPageable(pageNum, sortBy));

        return results.map(RecruitScrapResponseDto::from);
    }

    public Page<TrainingScrapResponseDto> getTrainingScrapList(
            CustomUserDetails customUserDetails, int pageNum, String locName, String sortBy
    ){

        Specification<MemberTrainingScrap> spec
                = MemberTrainingScrapSpecification.matchesFilter(customUserDetails.getId(), locName);

        Page<MemberTrainingScrap> results
                = memberTrainingScrapRepository.findAll(spec, getPageable(pageNum, sortBy));

        return results.map(TrainingScrapResponseDto::from);
    }

    private Pageable getPageable(int pageNum, String sortBy){
        return PageRequest.of(
                pageNum,
                PAGE_SIZE,
                SortBy.fromName(sortBy).getSort()
        );
    }
}
