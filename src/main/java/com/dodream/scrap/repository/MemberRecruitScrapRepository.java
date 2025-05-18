package com.dodream.scrap.repository;

import com.dodream.scrap.domain.MemberRecruitScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecruitScrapRepository  extends JpaRepository<MemberRecruitScrap, Long> {

    boolean existsByRecruitIdAndMemberId(String postId, Long memberId);
}
