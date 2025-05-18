package com.dodream.recruit.repository;

import com.dodream.member.domain.Member;
import com.dodream.recruit.domain.MemberRecruitScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecruitScrapRepository  extends JpaRepository<MemberRecruitScrap, Long> {

    boolean existsByRecruitIdAndMemberId(String postId, Long memberId);
}
