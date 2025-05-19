package com.dodream.scrap.repository;

import com.dodream.scrap.domain.MemberTrainingScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTrainingScrapRepository extends JpaRepository<MemberTrainingScrap, Long> {

    boolean existsByTrainingIdAndMemberId(String trainingId, Long memberId);

    int countByMemberId(Long memberId);
}
