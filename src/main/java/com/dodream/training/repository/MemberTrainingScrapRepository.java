package com.dodream.training.repository;

import com.dodream.training.domain.MemberTrainingScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTrainingScrapRepository extends JpaRepository<MemberTrainingScrap, Long> {

    boolean existsByTrainingIdAndMemberId(String trainingId, Long memberId);
}
