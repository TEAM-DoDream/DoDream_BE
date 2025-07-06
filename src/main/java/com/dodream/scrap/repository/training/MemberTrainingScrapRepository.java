package com.dodream.scrap.repository.training;

import com.dodream.member.domain.Member;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MemberTrainingScrapRepository extends JpaRepository<MemberTrainingScrap, Long>, MemberTrainingScrapRepositoryCustom {

    Optional<MemberTrainingScrap> findByTrainingIdAndMemberId(String trainingId, Long memberId);

    boolean existsByTrainingIdAndMemberId(String trainingId, Long memberId);

    int countByMemberId(Long memberId);

    void deleteAllByMember(Member member);

}
