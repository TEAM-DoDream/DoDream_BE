package com.dodream.scrap.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.Category;
import com.dodream.scrap.dto.response.IsScrapCheckedResponse;
import com.dodream.scrap.dto.response.RecruitScrapResponseDto;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.scrap.repository.recruit.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.training.MemberTrainingScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ScrapSearchService {

    private final MemberRecruitScrapRepository memberRecruitScrapRepository;
    private final MemberTrainingScrapRepository memberTrainingScrapRepository;

    private final int PAGE_SIZE = 6;

    public Page<RecruitScrapResponseDto> getRecruitScrapList(
            CustomUserDetails customUserDetails, int pageNum, String locName, String sortBy
    ){
        Page<MemberRecruitScrap> results = memberRecruitScrapRepository.searchWithFilter(
                customUserDetails.getId(), locName, sortBy, PAGE_SIZE, pageNum
        );

        return results.map(RecruitScrapResponseDto::from);
    }

    public Page<TrainingScrapResponseDto> getTrainingScrapList(
            CustomUserDetails customUserDetails, int pageNum, String locName, String sortBy
    ){
        Page<MemberTrainingScrap> results = memberTrainingScrapRepository.searchWithFilter(
                customUserDetails.getId(), locName, sortBy, PAGE_SIZE, pageNum
        );

        return results.map(TrainingScrapResponseDto::from);
    }

    public List<IsScrapCheckedResponse> isScrapCheck(
            CustomUserDetails customUserDetails,
            Category category,
            List<String> idList
    ) {
        Long memberId = customUserDetails.getId();
        BiPredicate<String, Long> existsPredicate = getExistsPredicate(category);

        return IntStream.range(0, idList.size())
                .mapToObj(i -> new IsScrapCheckedResponse(
                        i,
                        existsPredicate.test(idList.get(i), memberId)  // ← `.test()` 사용
                ))
                .toList();
    }

    private BiPredicate<String, Long> getExistsPredicate(Category category) {
        if (category == Category.RECRUIT) {
            return memberRecruitScrapRepository::existsByRecruitIdAndMemberId;
        } else {
            return memberTrainingScrapRepository::existsByTrainingIdAndMemberId;
        }
    }
}
