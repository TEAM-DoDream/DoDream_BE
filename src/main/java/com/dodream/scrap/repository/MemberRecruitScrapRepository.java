package com.dodream.scrap.repository;

import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MemberRecruitScrapRepository extends JpaRepository<MemberRecruitScrap, Long>, JpaSpecificationExecutor<MemberRecruitScrap> {

    Optional<MemberRecruitScrap> findByRecruitIdAndMemberId(String recruitId, Long memberId);

    boolean existsByRecruitIdAndMemberId(String recruitId, Long memberId);

    int countByMemberId(Long memberId);

    void deleteByRecruitIdAndMemberId(String recruitId, Long memberId);
}
