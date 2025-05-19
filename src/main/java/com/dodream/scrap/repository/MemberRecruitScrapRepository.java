package com.dodream.scrap.repository;

import com.dodream.scrap.domain.MemberRecruitScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRecruitScrapRepository  extends JpaRepository<MemberRecruitScrap, Long> {

    Optional<MemberRecruitScrap> findByRecruitIdAndMemberId(String recruitId, Long memberId);

    boolean existsByRecruitIdAndMemberId(String recruitId, Long memberId);

    int countByMemberId(Long memberId);

    void deleteByRecruitIdAndMemberId(String recruitId, Long memberId);
}
