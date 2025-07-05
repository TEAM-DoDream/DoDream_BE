package com.dodream.scrap.repository.recruit;

import com.dodream.member.domain.Member;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRecruitScrapRepository extends JpaRepository<MemberRecruitScrap, Long>, MemberRecruitScrapRepositoryCustom {

    Optional<MemberRecruitScrap> findByRecruitIdAndMemberId(String recruitId, Long memberId);

    boolean existsByRecruitIdAndMemberId(String recruitId, Long memberId);

    int countByMemberId(Long memberId);

    void deleteByRecruitIdAndMemberId(String recruitId, Long memberId);

    void deleteAllByMember(Member member);
}
